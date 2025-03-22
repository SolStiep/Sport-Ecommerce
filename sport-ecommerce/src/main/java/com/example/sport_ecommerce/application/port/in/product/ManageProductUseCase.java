package com.example.sport_ecommerce.application.port.in.product;

import com.example.sport_ecommerce.application.model.command.ProductCommand;
import com.example.sport_ecommerce.application.model.response.ProductResponse;

import java.util.UUID;

public interface ManageProductUseCase {
    ProductResponse create(ProductCommand product);
    ProductResponse update(ProductCommand product);
    void delete(UUID productId);
}
