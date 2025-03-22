package com.example.sport_ecommerce.application.mapper;

import com.example.sport_ecommerce.application.model.response.PartOptionResponse;
import com.example.sport_ecommerce.application.model.response.PartResponse;
import com.example.sport_ecommerce.application.model.response.ProductResponse;
import com.example.sport_ecommerce.domain.model.Part;
import com.example.sport_ecommerce.domain.model.PartOption;
import com.example.sport_ecommerce.domain.model.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductResponseMapper {
    public ProductResponse toResponse(Product product) {
        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .categoryId(product.getCategory().getId())
                .parts(product.getParts().stream()
                        .map(this::toPartResponse)
                        .toList())
                .build();
    }

    private PartResponse toPartResponse(Part part) {
        return PartResponse.builder()
                .id(part.getId())
                .name(part.getName())
                .options(part.getOptions().stream()
                        .map(this::toPartOptionResponse)
                        .toList())
                .build();
    }

    private PartOptionResponse toPartOptionResponse(PartOption option) {
        return PartOptionResponse.builder()
                .id(option.getId())
                .name(option.getName())
                .price(option.getPrice())
                .inStock(option.isInStock())
                .build();
    }
}
