package com.example.sport_ecommerce.application.mapper;

import com.example.sport_ecommerce.application.model.command.CategoryCommand;
import com.example.sport_ecommerce.application.model.response.CategoryResponse;
import com.example.sport_ecommerce.domain.model.Category;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    Category toDomain(CategoryCommand command);

    CategoryResponse toResponse(Category category);
}
