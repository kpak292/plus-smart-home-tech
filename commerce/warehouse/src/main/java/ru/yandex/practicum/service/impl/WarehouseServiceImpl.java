package ru.yandex.practicum.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.clients.StoreClient;
import ru.yandex.practicum.dto.cart.ShoppingCartDto;
import ru.yandex.practicum.dto.product.QuantityState;
import ru.yandex.practicum.dto.warehouse.*;
import ru.yandex.practicum.exception.NoOrderFoundException;
import ru.yandex.practicum.exception.NoSpecifiedProductInWarehouseException;
import ru.yandex.practicum.exception.ProductInShoppingCartLowQuantityInWarehouse;
import ru.yandex.practicum.exception.SpecifiedProductAlreadyInWarehouseException;
import ru.yandex.practicum.mapper.WarehouseMapper;
import ru.yandex.practicum.repository.OrderBooking;
import ru.yandex.practicum.repository.OrderBookingRepository;
import ru.yandex.practicum.repository.WarehouseProduct;
import ru.yandex.practicum.repository.WarehouseProductRepository;
import ru.yandex.practicum.service.WarehouseService;

import java.security.SecureRandom;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class WarehouseServiceImpl implements WarehouseService {
    private static final String[] ADDRESSES =
            new String[]{"ADDRESS_1", "ADDRESS_2"};
    private static final String CURRENT_ADDRESS =
            ADDRESSES[Random.from(new SecureRandom()).nextInt(0, 1)];

    private final WarehouseProductRepository warehouseProductRepository;
    private final OrderBookingRepository orderBookingRepository;
    private final StoreClient storeClient;

    @Override
    public void add(NewProductInWarehouseRequest request) {
        if (warehouseProductRepository.findById(request.getProductId()).isPresent()) {
            throw new SpecifiedProductAlreadyInWarehouseException("Specified product already in warehouse: " +
                    request.getProductId());
        }

        WarehouseProduct product = WarehouseMapper.INSTANCE.toProductEntity(request);
        product.setQuantity(0L);
        warehouseProductRepository.save(product);
    }

    @Override
    public BookedProductsDto check(ShoppingCartDto cart) {
        return checkProducts(cart.getProducts());
    }

    @Override
    public void addProducts(AddProductToWarehouseRequest request) {
        WarehouseProduct product = warehouseProductRepository.findById(request.getProductId())
                .orElseThrow(() -> new NoSpecifiedProductInWarehouseException("Product not found: " +
                        request.getProductId()));

        product.setQuantity(product.getQuantity() + request.getQuantity());

        warehouseProductRepository.save(product);

        storeClient.setQuantity(product.getProductId(), getState(product.getQuantity()));
    }

    @Override
    public AddressDto getAddress() {
        return AddressDto.builder()
                .city(CURRENT_ADDRESS)
                .street(CURRENT_ADDRESS)
                .house(CURRENT_ADDRESS)
                .flat(CURRENT_ADDRESS)
                .build();
    }

    @Override
    public void ShipProducts(ShippedToDeliveryRequest request) {
        OrderBooking orderBooking = orderBookingRepository.findByOrderId(request.getOrderId())
                .orElseThrow(() -> new NoOrderFoundException("Order not found: " +
                        request.getOrderId()));

        orderBooking.setDeliveryId(request.getDeliveryId());

        orderBookingRepository.save(orderBooking);
    }

    @Override
    @Transactional
    public BookedProductsDto assemblyProducts(AssemblyProductsForOrderRequest request) {
        BookedProductsDto bookedProductsDto = checkProducts(request.getProducts());

        request.getProducts().forEach((productId, quantity) -> {
            WarehouseProduct product = warehouseProductRepository.findById(productId).get();
            product.setQuantity(product.getQuantity() - quantity);
        });

        OrderBooking orderBooking = OrderBooking.builder()
                .orderId(request.getOrderId())
                .products(request.getProducts())
                .build();

        orderBookingRepository.save(orderBooking);

        return bookedProductsDto;
    }

    private QuantityState getState(Long quantity) {
        if (quantity == null || quantity <= 0) {
            return QuantityState.ENDED;
        } else if (quantity < 10) {
            return QuantityState.FEW;
        } else if (quantity <= 100) {
            return QuantityState.ENOUGH;
        } else {
            return QuantityState.MANY;
        }
    }

    private BookedProductsDto checkProducts(Map<UUID, Long> products) {
        StringBuilder sb = new StringBuilder();
        double deliveryWeight = 0D;
        double deliveryVolume = 0D;
        boolean fragile = false;

        for (var entry : products.entrySet()) {
            Optional<WarehouseProduct> product = warehouseProductRepository.findById(entry.getKey());

            if (product.isEmpty() || product.get().getQuantity() < entry.getValue()) {
                sb.append("Product ").append(entry.getKey()).append(" not found or not enough quantity").append("\n");
            } else {
                deliveryWeight += product.get().getWeight() * entry.getValue();
                deliveryVolume += (product.get().getDimensionDepth() *
                        product.get().getDimensionHeight() *
                        product.get().getDimensionWidth()) * entry.getValue();
                if (!fragile && product.get().getFragile()) {
                    fragile = true;
                }
            }
        }

        if (!sb.isEmpty()) {
            throw new ProductInShoppingCartLowQuantityInWarehouse(sb.toString());
        }

        return BookedProductsDto.builder()
                .fragile(fragile)
                .deliveryWeight(deliveryWeight)
                .deliveryVolume(deliveryVolume)
                .build();
    }
}
