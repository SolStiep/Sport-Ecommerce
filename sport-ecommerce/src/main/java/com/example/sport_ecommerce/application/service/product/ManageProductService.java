package com.example.sport_ecommerce.application.service.product;

import com.example.sport_ecommerce.application.mapper.ProductCommandMapper;
import com.example.sport_ecommerce.application.mapper.ProductSummaryMapper;
import com.example.sport_ecommerce.application.model.command.ProductCommand;
import com.example.sport_ecommerce.application.model.response.ProductSummaryResponse;
import com.example.sport_ecommerce.application.port.in.product.ManageProductUseCase;
import com.example.sport_ecommerce.application.port.out.CategoryRepositoryPort;
import com.example.sport_ecommerce.application.port.out.ProductRepositoryPort;
import com.example.sport_ecommerce.domain.model.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ManageProductService implements ManageProductUseCase {

    private final ProductRepositoryPort productRepository;
    private final CategoryRepositoryPort categoryRepository;
    private final ProductCommandMapper productMapper;
    private final ProductSummaryMapper responseMapper;

    @Override
    public ProductSummaryResponse create(ProductCommand command) {
        Category category = categoryRepository.findById(command.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));

        Product product = productMapper.toDomain(command, category);
        Product saved = productRepository.save(product);
        return responseMapper.toResponse(saved);
    }

    @Override
    public ProductSummaryResponse update(ProductCommand command) {
        Category category = categoryRepository.findById(command.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));

        Product product = productMapper.toDomain(command, category);
        product.setId(command.getId());
        Product updated = productRepository.save(product);

        return responseMapper.toResponse(updated);
    }

    @Override
    public void delete(UUID productId) {
        productRepository.deleteById(productId);
    }
}
