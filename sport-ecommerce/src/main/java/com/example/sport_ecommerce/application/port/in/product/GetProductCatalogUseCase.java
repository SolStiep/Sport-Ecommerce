package com.example.sport_ecommerce.application.port.in.product;

import com.example.sport_ecommerce.application.model.response.ProductSummaryResponse;

import java.util.List;

public interface GetProductCatalogUseCase {
    List<ProductSummaryResponse> getAllProducts();
}
