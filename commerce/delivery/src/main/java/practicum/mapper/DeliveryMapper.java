package practicum.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import practicum.repository.Delivery;
import ru.yandex.practicum.dto.delivery.DeliveryDto;

@Mapper(componentModel = "spring")
public interface DeliveryMapper {
    DeliveryMapper INSTANCE = Mappers.getMapper(DeliveryMapper.class);

    @Mapping(target = "countryFrom", source = "deliveryDto.fromAddress.country")
    @Mapping(target = "countryTo", source = "deliveryDto.toAddress.country")
    @Mapping(target = "cityFrom", source = "deliveryDto.fromAddress.city")
    @Mapping(target = "cityTo", source = "deliveryDto.toAddress.city")
    @Mapping(target = "streetFrom", source = "deliveryDto.fromAddress.street")
    @Mapping(target = "streetTo", source = "deliveryDto.toAddress.street")
    @Mapping(target = "houseFrom", source = "deliveryDto.fromAddress.house")
    @Mapping(target = "houseTo", source = "deliveryDto.toAddress.house")
    @Mapping(target = "flatFrom", source = "deliveryDto.fromAddress.flat")
    @Mapping(target = "flatTo", source = "deliveryDto.toAddress.flat")
    Delivery toEntity(DeliveryDto deliveryDto);

    @Mapping(target = "fromAddress.country", source = "countryFrom")
    @Mapping(target = "fromAddress.city", source = "cityFrom")
    @Mapping(target = "fromAddress.street", source = "streetFrom")
    @Mapping(target = "fromAddress.house", source = "houseFrom")
    @Mapping(target = "fromAddress.flat", source = "flatFrom")
    @Mapping(target = "toAddress.country", source = "countryTo")
    @Mapping(target = "toAddress.city", source = "cityTo")
    @Mapping(target = "toAddress.street", source = "streetTo")
    @Mapping(target = "toAddress.house", source = "houseTo")
    @Mapping(target = "toAddress.flat", source = "flatTo")
    DeliveryDto toDto(Delivery delivery);
}
