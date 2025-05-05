package ru.yandex.practicum.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import ru.yandex.practicum.dto.warehouse.NewProductInWarehouseRequest;
import ru.yandex.practicum.repository.WarehouseProduct;

@Mapper(componentModel = "spring")
public interface WarehouseMapper {
    WarehouseMapper INSTANCE = Mappers.getMapper(WarehouseMapper.class);

    @Mapping(target = "dimensionWidth", source = "request.dimension.width")
    @Mapping(target = "dimensionHeight", source = "request.dimension.height")
    @Mapping(target = "dimensionDepth", source = "request.dimension.depth")
    @Mapping(target = "quantity", ignore = true)
    WarehouseProduct toProductEntity(NewProductInWarehouseRequest request);
}
