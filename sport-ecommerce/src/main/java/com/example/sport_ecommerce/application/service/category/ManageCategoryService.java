package com.example.sport_ecommerce.application.service.category;

import com.example.sport_ecommerce.application.mapper.CategoryMapper;
import com.example.sport_ecommerce.application.model.command.CategoryCommand;
import com.example.sport_ecommerce.application.model.response.CategoryResponse;
import com.example.sport_ecommerce.application.port.in.category.ManageCategoryUseCase;
import com.example.sport_ecommerce.application.port.out.CategoryRepositoryPort;
import com.example.sport_ecommerce.domain.model.Category;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ManageCategoryService implements ManageCategoryUseCase {
    private final CategoryRepositoryPort categoryRepository;
    private final CategoryMapper categoryMapper;

    @Override
    public CategoryResponse create(CategoryCommand command) {
        Category category = categoryMapper.toDomain(command);
        Category saved = categoryRepository.save(category);
        return categoryMapper.toResponse(saved);
    }

    @Override
    public CategoryResponse update(CategoryCommand command) {
        if (command.getId() == null) {
            throw new IllegalArgumentException("Category ID is required for update.");
        }
        Category category = categoryMapper.toDomain(command);
        Category updated = categoryRepository.save(category);
        return categoryMapper.toResponse(updated);
    }

    @Override
    public void delete(UUID categoryId) {
        categoryRepository.deleteById(categoryId);
    }
}
