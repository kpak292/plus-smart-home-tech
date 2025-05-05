package ru.yandex.practicum.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.exception.ProductNotFoundException;
import ru.yandex.practicum.dto.product.ProductCategory;
import ru.yandex.practicum.dto.product.ProductDto;
import ru.yandex.practicum.dto.product.ProductState;
import ru.yandex.practicum.dto.product.SetProductQuantityStateRequest;
import ru.yandex.practicum.mapper.ProductMapper;
import ru.yandex.practicum.repository.Product;
import ru.yandex.practicum.repository.ProductRepository;
import ru.yandex.practicum.service.ProductService;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;

    @Override
    public Page<ProductDto> findAll(String category, Pageable pageable) {
        return productRepository.findAllByProductCategory(ProductCategory.valueOf(category), pageable)
                .map(ProductMapper.INSTANCE::toDto);
    }

    @Override
    public ProductDto create(ProductDto productDto) {
        Product product = ProductMapper.INSTANCE.toEntity(productDto);
        product = productRepository.save(product);
        return ProductMapper.INSTANCE.toDto(product);
    }

    @Override
    @Transactional
    public ProductDto update(ProductDto productDto) {
        Product product = productRepository.findById(productDto.getProductId())
                .orElseThrow(() -> new ProductNotFoundException("Product not found with id: " + productDto.getProductId()));

        ProductMapper.INSTANCE.update(productDto, product);

        return ProductMapper.INSTANCE.toDto(product);
    }

    @Override
    @Transactional
    public Boolean delete(UUID id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product not found with id: " + id));

        product.setProductState(ProductState.DEACTIVATE);

        return true;
    }

    @Override
    @Transactional
    public Boolean setQuantity(SetProductQuantityStateRequest request) {
        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new ProductNotFoundException("Product not found with id: " + request.getProductId()));

        product.setQuantityState(request.getQuantityState());

        return true;
    }

    @Override
    public ProductDto findById(UUID id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product not found with id: " + id));

        return ProductMapper.INSTANCE.toDto(product);
    }
}
