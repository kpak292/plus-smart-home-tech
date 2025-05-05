package ru.yandex.practicum.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import ru.yandex.practicum.dto.cart.ShoppingCartDto;
import ru.yandex.practicum.repository.Cart;

@Mapper(componentModel = "spring")
public interface CartMapper {
    CartMapper INSTANCE = Mappers.getMapper(CartMapper.class);

    @Mapping(target = "userName", ignore = true)
    @Mapping(target = "active", ignore = true)
    Cart toEntity(ShoppingCartDto shoppingCartDto);

    ShoppingCartDto toDto(Cart cart);
}
