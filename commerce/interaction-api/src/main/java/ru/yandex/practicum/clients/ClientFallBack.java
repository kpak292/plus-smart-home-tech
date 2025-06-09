package ru.yandex.practicum.clients;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.dto.cart.ShoppingCartDto;
import ru.yandex.practicum.dto.delivery.DeliveryDto;
import ru.yandex.practicum.dto.order.OrderDto;
import ru.yandex.practicum.dto.payment.PaymentDto;
import ru.yandex.practicum.dto.product.ProductDto;
import ru.yandex.practicum.dto.product.QuantityState;
import ru.yandex.practicum.dto.warehouse.AddressDto;
import ru.yandex.practicum.dto.warehouse.AssemblyProductsForOrderRequest;
import ru.yandex.practicum.dto.warehouse.BookedProductsDto;
import ru.yandex.practicum.dto.warehouse.ShippedToDeliveryRequest;
import ru.yandex.practicum.exception.ServiceUnavailableException;

import java.util.UUID;

@Component
public class ClientFallBack implements StoreClient, WarehouseClient, OrderClient, DeliveryClient, PaymentClient {


    @Override
    public DeliveryDto planDelivery(DeliveryDto delivery) {
        throw new ServiceUnavailableException("Service unavailable");
    }

    @Override
    public Double deliveryCost(OrderDto orderDto) {
        throw new ServiceUnavailableException("Service unavailable");
    }

    @Override
    public void deliveryFailed(UUID orderId) {
        throw new ServiceUnavailableException("Service unavailable");
    }

    @Override
    public void delivery(UUID orderId) {
        throw new ServiceUnavailableException("Service unavailable");
    }

    @Override
    public OrderDto cancelDelivery(UUID orderId) {
        throw new ServiceUnavailableException("Service unavailable");
    }

    @Override
    public OrderDto confirmDelivery(UUID orderId) {
        throw new ServiceUnavailableException("Service unavailable");
    }

    @Override
    public OrderDto paymentSuccess(UUID orderId) {
        throw new ServiceUnavailableException("Service unavailable");
    }

    @Override
    public OrderDto paymentFailed(UUID orderId) {
        throw new ServiceUnavailableException("Service unavailable");
    }

    @Override
    public OrderDto orderAssembly(UUID orderId) {
        throw new ServiceUnavailableException("Service unavailable");
    }

    @Override
    public Double productCost(OrderDto orderDto) {
        throw new ServiceUnavailableException("Service unavailable");
    }

    @Override
    public Double getTotalCost(OrderDto orderDto) {
        throw new ServiceUnavailableException("Service unavailable");
    }

    @Override
    public PaymentDto payment(OrderDto orderDto) {
        throw new ServiceUnavailableException("Service unavailable");
    }

    @Override
    public Boolean setQuantity(UUID productId, QuantityState quantityState) {
        throw new ServiceUnavailableException("Service unavailable");
    }

    @Override
    public ProductDto getProduct(UUID productId) {
        throw new ServiceUnavailableException("Service unavailable");
    }

    @Override
    public BookedProductsDto checkCart(ShoppingCartDto cart) {
        throw new ServiceUnavailableException("Service unavailable");
    }

    @Override
    public BookedProductsDto assemblyProductForOrderFromShoppingCart(AssemblyProductsForOrderRequest request) {
        throw new ServiceUnavailableException("Service unavailable");
    }

    @Override
    public AddressDto getWarehouseAddress() {
        throw new ServiceUnavailableException("Service unavailable");
    }

    @Override
    public void shippedToDelivery(ShippedToDeliveryRequest request) {
        throw new ServiceUnavailableException("Service unavailable");
    }
}
