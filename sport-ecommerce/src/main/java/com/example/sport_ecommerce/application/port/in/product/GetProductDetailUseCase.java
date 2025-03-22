package com.example.sport_ecommerce.application.port.in.product;

import com.example.sport_ecommerce.application.model.response.ProductResponse;

import java.util.UUID;

public interface GetProductDetailUseCase {
    ProductResponse getProductById(UUID productId);
}
