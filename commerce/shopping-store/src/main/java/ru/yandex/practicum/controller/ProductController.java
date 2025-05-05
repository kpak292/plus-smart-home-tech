package ru.yandex.practicum.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.clients.StoreClient;
import ru.yandex.practicum.clients.WarehouseClient;
import ru.yandex.practicum.dto.product.ProductDto;
import ru.yandex.practicum.dto.product.QuantityState;
import ru.yandex.practicum.dto.product.SetProductQuantityStateRequest;
import ru.yandex.practicum.service.ProductService;

import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/api/v1/shopping-store")
@RequiredArgsConstructor
public class ProductController implements StoreClient {
    private final ProductService productService;
    private final WarehouseClient warehouseClient;

    @GetMapping
    public Page<ProductDto> getProducts(@RequestParam String category,
                                        @RequestParam(defaultValue = "0") Integer page,
                                        @RequestParam(defaultValue = "1") Integer size,
                                        @RequestParam(defaultValue = "productName") String sort) {
        log.info("Get products for category: {}", category);
        Pageable pageable = PageRequest.of(page, size, Sort.Direction.ASC, sort);
        return productService.findAll(category, pageable);
    }

    @PutMapping
    public ProductDto createProduct(@RequestBody @Valid ProductDto product) {
        log.info("Create product: {}", product);
        return productService.create(product);
    }

    @PostMapping
    public ProductDto updateProduct(@RequestBody @Valid ProductDto product) {
        log.info("Update product: {}", product);
        return productService.update(product);
    }

    @PostMapping("/removeProductFromStore")
    public Boolean removeProductFromStore(@RequestBody UUID id) {
        log.info("Remove product from store: {}", id);
        return productService.delete(id);
    }

    @GetMapping("/{productId}")
    public ProductDto getProductById(@PathVariable UUID productId) {
        log.info("Get product by id: {}", productId);
        return productService.findById(productId);
    }

    @Override
    @PostMapping("/quantityState")
    public Boolean setQuantity(UUID productId, QuantityState quantityState) {
        SetProductQuantityStateRequest request = SetProductQuantityStateRequest.builder()
                .productId(productId)
                .quantityState(quantityState)
                .build();

        log.info("Set quantity state: {}", request);
        return productService.setQuantity(request);

    }
}
