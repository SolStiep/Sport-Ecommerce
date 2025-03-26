package com.example.sport_ecommerce.infrastructure.adapter.web.controller;

import com.example.sport_ecommerce.application.model.command.ProductCommand;
import com.example.sport_ecommerce.application.model.response.ProductResponse;
import com.example.sport_ecommerce.application.model.response.ProductSummaryResponse;
import com.example.sport_ecommerce.application.port.in.product.GetProductCatalogUseCase;
import com.example.sport_ecommerce.application.port.in.product.GetProductDetailUseCase;
import com.example.sport_ecommerce.application.port.in.product.ManageProductUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final ManageProductUseCase manageProductUseCase;
    private final GetProductCatalogUseCase getProductCatalogUseCase;
    private final GetProductDetailUseCase getProductDetailUseCase;

    @PostMapping
    public ResponseEntity<ProductSummaryResponse> createProduct(@RequestBody @Valid ProductCommand command) {
        ProductSummaryResponse response = manageProductUseCase.create(command);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public List<ProductSummaryResponse> getAllProducts() {
        return getProductCatalogUseCase.getAllProducts();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getProductById(@PathVariable UUID id) {
        ProductResponse product = getProductDetailUseCase.getProductById(id);
        return ResponseEntity.ok(product);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable UUID id) {
        manageProductUseCase.delete(id);
        return ResponseEntity.noContent().build();
    }
}

