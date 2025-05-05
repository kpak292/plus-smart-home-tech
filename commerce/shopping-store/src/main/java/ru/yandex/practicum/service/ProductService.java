package ru.yandex.practicum.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.yandex.practicum.dto.product.ProductDto;
import ru.yandex.practicum.dto.product.SetProductQuantityStateRequest;

import java.util.UUID;

public interface ProductService {
    Page<ProductDto> findAll(String category, Pageable pageable);

    ProductDto create(ProductDto productDto);

    ProductDto update(ProductDto productDto);

    Boolean delete(UUID id);

    Boolean setQuantity(SetProductQuantityStateRequest request);

    ProductDto findById(UUID id);
}
