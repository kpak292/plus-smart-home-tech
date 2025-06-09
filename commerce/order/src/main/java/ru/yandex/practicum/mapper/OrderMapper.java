package ru.yandex.practicum.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ru.yandex.practicum.dto.order.CreateNewOrderRequest;
import ru.yandex.practicum.dto.order.OrderDto;
import ru.yandex.practicum.dto.order.OrderState;
import ru.yandex.practicum.repository.Order;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    OrderMapper INSTANCE = Mappers.getMapper(OrderMapper.class);

    OrderDto toDto(Order order);

    default Order newOrder(CreateNewOrderRequest request, String username) {
        Order order = new Order();

        order.setShoppingCartId(request.getShoppingCart().getShoppingCartId());
        order.setProducts(request.getShoppingCart().getProducts());
        order.setDeliveryCountry(request.getDeliveryAddress().getCountry());
        order.setDeliveryCity(request.getDeliveryAddress().getCity());
        order.setDeliveryStreet(request.getDeliveryAddress().getStreet());
        order.setDeliveryHouse(request.getDeliveryAddress().getHouse());
        order.setDeliveryFlat(request.getDeliveryAddress().getFlat());
        order.setState(OrderState.NEW);

        return order;
    }
}
