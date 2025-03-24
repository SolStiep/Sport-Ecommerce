package com.example.sport_ecommerce.infrastructure.adapter.persistence.mapper;

import com.example.sport_ecommerce.domain.model.Product;

import java.util.UUID;

public interface ProductResolver {
    Product resolveProduct(UUID productId);
}
