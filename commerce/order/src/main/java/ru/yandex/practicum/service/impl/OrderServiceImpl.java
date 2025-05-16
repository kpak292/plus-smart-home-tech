package ru.yandex.practicum.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.clients.DeliveryClient;
import ru.yandex.practicum.clients.PaymentClient;
import ru.yandex.practicum.clients.WarehouseClient;
import ru.yandex.practicum.dto.warehouse.AssemblyProductsForOrderRequest;
import ru.yandex.practicum.dto.delivery.DeliveryDto;
import ru.yandex.practicum.dto.delivery.DeliveryState;
import ru.yandex.practicum.dto.order.CreateNewOrderRequest;
import ru.yandex.practicum.dto.order.OrderDto;
import ru.yandex.practicum.dto.order.OrderState;
import ru.yandex.practicum.dto.order.ProductReturnRequest;
import ru.yandex.practicum.dto.payment.PaymentDto;
import ru.yandex.practicum.dto.warehouse.AddressDto;
import ru.yandex.practicum.dto.warehouse.BookedProductsDto;
import ru.yandex.practicum.exception.NoOrderFoundException;
import ru.yandex.practicum.exception.NotAuthorizedUserException;
import ru.yandex.practicum.mapper.OrderMapper;
import ru.yandex.practicum.repository.Order;
import ru.yandex.practicum.repository.OrderRepository;
import ru.yandex.practicum.service.OrderService;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    OrderRepository orderRepository;
    DeliveryClient deliveryClient;
    PaymentClient paymentClient;
    WarehouseClient warehouseClient;

    @Override
    public List<OrderDto> getOrders(String username) {
        if (username == null || username.isEmpty()) {
            throw new NotAuthorizedUserException("Username is empty");
        }

        return orderRepository.findAllByUsername(username).stream()
                .map(OrderMapper.INSTANCE::toDto)
                .toList();
    }

    @Override
    public OrderDto createOrder(CreateNewOrderRequest request, String username) {
        if (username == null || username.isEmpty()) {
            throw new NotAuthorizedUserException("Username is empty");
        }
        Order order = OrderMapper.INSTANCE.newOrder(request, username);

        order.setProductPrice(paymentClient.productCost(OrderMapper.INSTANCE.toDto(order)));

        order = orderRepository.save(order);
        return OrderMapper.INSTANCE.toDto(order);
    }

    @Override
    public OrderDto returnProducts(ProductReturnRequest request) {
        Order order = getOrder(request.getOrderId());
        Map<UUID, Long> products = order.getProducts();
        request.getProducts().forEach((productId, quantity) -> {
            if (products.containsKey(productId)) {
                products.compute(productId, (k, currentQuantity) -> currentQuantity - quantity);
            } else {
                throw new NoOrderFoundException("Product not found");
            }
        });

        order.setProducts(products);
        order = orderRepository.save(order);

        return OrderMapper.INSTANCE.toDto(order);
    }

    @Override
    public OrderDto payOrder(UUID orderId) {
        Order order = getOrder(orderId);

        PaymentDto payment = paymentClient.payment(OrderMapper.INSTANCE.toDto(order));
        order.setPaymentId(payment.getPaymentId());
        order.setState(OrderState.ON_PAYMENT);

        order = orderRepository.save(order);
        return OrderMapper.INSTANCE.toDto(order);
    }

    @Override
    public OrderDto cancelPayOrder(UUID orderId) {
        Order order = getOrder(orderId);
        order.setState(OrderState.PAYMENT_FAILED);
        order = orderRepository.save(order);
        return OrderMapper.INSTANCE.toDto(order);
    }

    @Override
    public OrderDto deliverOrder(UUID orderId) {
        Order order = getOrder(orderId);

        order.setState(OrderState.ON_DELIVERY);
        AddressDto address = AddressDto.builder()
                .country(order.getDeliveryCountry())
                .city(order.getDeliveryCity())
                .street(order.getDeliveryStreet())
                .house(order.getDeliveryHouse())
                .flat(order.getDeliveryFlat())
                .build();
        DeliveryDto delivery = new DeliveryDto();
        delivery.setOrderId(order.getOrderId());
        delivery.setToAddress(address);
        delivery.setFromAddress(warehouseClient.getWarehouseAddress());
        delivery.setDeliveryState(DeliveryState.CREATED);

        DeliveryDto deliveryDto = deliveryClient.planDelivery(delivery);
        order.setDeliveryId(deliveryDto.getDeliveryId());

        order = orderRepository.save(order);
        return OrderMapper.INSTANCE.toDto(order);
    }

    @Override
    public OrderDto cancelDeliveryOrder(UUID orderId) {
        Order order = getOrder(orderId);
        order.setState(OrderState.DELIVERY_FAILED);
        order = orderRepository.save(order);
        return OrderMapper.INSTANCE.toDto(order);
    }

    @Override
    public OrderDto completeDelivery(UUID orderId) {
        Order order = getOrder(orderId);
        order.setState(OrderState.DELIVERED);
        order = orderRepository.save(order);
        return OrderMapper.INSTANCE.toDto(order);
    }

    @Override
    public OrderDto calculateTotalPrice(UUID orderId) {
        Order order = getOrder(orderId);

        order.setTotalPrice(paymentClient.getTotalCost(OrderMapper.INSTANCE.toDto(order)));
        order = orderRepository.save(order);
        return OrderMapper.INSTANCE.toDto(order);
    }

    @Override
    public OrderDto calculateDeliveryPrice(UUID orderId) {
        Order order = getOrder(orderId);

        double deliveryPrice = deliveryClient.deliveryCost(OrderMapper.INSTANCE.toDto(order));
        order.setDeliveryPrice(deliveryPrice);

        order = orderRepository.save(order);
        return OrderMapper.INSTANCE.toDto(order);
    }

    @Override
    public OrderDto orderAssembly(UUID orderId) {
        Order order = getOrder(orderId);

        AssemblyProductsForOrderRequest request = AssemblyProductsForOrderRequest.builder()
                .orderId(order.getOrderId())
                .products(order.getProducts())
                .build();

        BookedProductsDto bookedProductsDto = warehouseClient.assemblyProductForOrderFromShoppingCart(request);
        order.setDeliveryWeight(bookedProductsDto.getDeliveryWeight());
        order.setDeliveryVolume(bookedProductsDto.getDeliveryVolume());
        order.setFragile(bookedProductsDto.getFragile());

        order.setState(OrderState.ASSEMBLED);
        order = orderRepository.save(order);
        return OrderMapper.INSTANCE.toDto(order);
    }

    @Override
    public OrderDto cancelOrderAssembly(UUID orderId) {
        Order order = getOrder(orderId);
        order.setState(OrderState.ASSEMBLY_FAILED);
        order = orderRepository.save(order);
        return OrderMapper.INSTANCE.toDto(order);
    }

    private Order getOrder(UUID orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new NoOrderFoundException("Order not found"));
    }
}
