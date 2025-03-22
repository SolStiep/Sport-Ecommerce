package com.example.sport_ecommerce.application.mapper;

import com.example.sport_ecommerce.application.model.command.CategoryCommand;
import com.example.sport_ecommerce.application.model.response.CategoryResponse;
import com.example.sport_ecommerce.domain.model.Category;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class CategoryMapper {
    public Category toDomain(CategoryCommand command) {
        return new Category(
                command.getId() != null ? command.getId() : UUID.randomUUID(),
                command.getName(),
                command.getDescription()
        );
    }

    public CategoryResponse toResponse(Category category) {
        return CategoryResponse.builder()
                .id(category.getId())
                .name(category.getName())
                .description(category.getDescription())
                .build();
    }
}
