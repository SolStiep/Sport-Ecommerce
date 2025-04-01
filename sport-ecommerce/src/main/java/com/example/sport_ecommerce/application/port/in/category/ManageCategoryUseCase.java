package com.example.sport_ecommerce.application.port.in.category;

import com.example.sport_ecommerce.application.model.command.CategoryCommand;
import com.example.sport_ecommerce.application.model.response.CategoryResponse;

import java.util.List;
import java.util.UUID;

public interface ManageCategoryUseCase {
    CategoryResponse create(CategoryCommand command);
    CategoryResponse update(CategoryCommand command);
    void delete(UUID orderId);
    List<CategoryResponse> getAll();
}
