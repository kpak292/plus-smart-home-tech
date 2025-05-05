package ru.yandex.practicum.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import ru.yandex.practicum.dto.warehouse.NewProductInWarehouseRequest;
import ru.yandex.practicum.repository.WarehouseProduct;

@Mapper(componentModel = "spring")
public interface WarehouseMapper {
    WarehouseMapper INSTANCE = Mappers.getMapper(WarehouseMapper.class);

    @Mapping(target = "dimensionWidth", source = "request.dimensionWidth")
    @Mapping(target = "dimensionHeight", source = "request.dimensionHeight")
    @Mapping(target = "dimensionDepth", source = "request.dimensionDepth")
    WarehouseProduct toProductEntity(NewProductInWarehouseRequest request);
}
