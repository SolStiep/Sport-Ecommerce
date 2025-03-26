package com.example.sport_ecommerce.application.port.in.product;

import com.example.sport_ecommerce.application.model.command.ProductCommand;
import com.example.sport_ecommerce.application.model.response.ProductResponse;
import com.example.sport_ecommerce.application.model.response.ProductSummaryResponse;

import java.util.UUID;

public interface ManageProductUseCase {
    ProductSummaryResponse create(ProductCommand product);
    ProductSummaryResponse update(ProductCommand product);
    void delete(UUID productId);
}
