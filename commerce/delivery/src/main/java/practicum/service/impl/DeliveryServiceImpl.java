package practicum.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import practicum.mapper.DeliveryMapper;
import practicum.repository.Delivery;
import practicum.repository.DeliveryRepository;
import practicum.service.DeliveryService;
import ru.yandex.practicum.clients.OrderClient;
import ru.yandex.practicum.clients.WarehouseClient;
import ru.yandex.practicum.dto.delivery.DeliveryDto;
import ru.yandex.practicum.dto.delivery.DeliveryState;
import ru.yandex.practicum.dto.order.OrderDto;
import ru.yandex.practicum.dto.warehouse.ShippedToDeliveryRequest;
import ru.yandex.practicum.exception.NoDeliveryFoundException;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DeliveryServiceImpl implements DeliveryService {
    DeliveryRepository deliveryRepository;
    OrderClient orderClient;
    WarehouseClient warehouseClient;

    @Override
    public DeliveryDto planDelivery(DeliveryDto deliveryDto) {
        Delivery delivery = DeliveryMapper.INSTANCE.toEntity(deliveryDto);
        delivery = deliveryRepository.save(delivery);

        return DeliveryMapper.INSTANCE.toDto(delivery);
    }

    @Override
    public Double deliveryCost(OrderDto orderDto) {
        Delivery delivery = getDelivery(orderDto.getOrderId());

        double price = 5.0;
        if (delivery.getStreetFrom().equals("ADDRESS_2")) {
            price = price * 3;
        } else if (delivery.getStreetFrom().equals("ADDRESS_1")) {
            price = price * 2;
        }

        if (orderDto.getFragile()) {
            price = price * 1.2;
        }

        price = price + orderDto.getDeliveryWeight() * 0.3;
        price = price + orderDto.getDeliveryVolume() * 0.2;

        if (!delivery.getStreetFrom().equals(delivery.getStreetTo())) {
            price = price * 1.2;
        }

        return price;
    }

    @Override
    public void deliverySuccessful(UUID orderId) {
        Delivery delivery = getDelivery(orderId);
        delivery.setDeliveryState(DeliveryState.DELIVERED);
        deliveryRepository.save(delivery);

        orderClient.delivery(orderId);
    }

    @Override
    public void deliveryFailed(UUID orderId) {
        Delivery delivery = getDelivery(orderId);
        delivery.setDeliveryState(DeliveryState.FAILED);
        deliveryRepository.save(delivery);

        orderClient.deliveryFailed(orderId);
    }

    @Override
    public void pick(UUID orderId) {
        Delivery delivery = getDelivery(orderId);

        delivery.setDeliveryState(DeliveryState.IN_PROGRESS);
        deliveryRepository.save(delivery);

        orderClient.orderAssembly(orderId);
        ShippedToDeliveryRequest request = ShippedToDeliveryRequest.builder()
                .orderId(orderId)
                .deliveryId(delivery.getDeliveryId())
                .build();
        warehouseClient.shippedToDelivery(request);
    }

    private Delivery getDelivery(UUID orderId) {
        return deliveryRepository.findDeliveryByOrderId(orderId)
                .orElseThrow(() -> new NoDeliveryFoundException("Delivery not found for order: " + orderId));
    }
}
