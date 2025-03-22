package com.example.sport_ecommerce.application.port.out;

import com.example.sport_ecommerce.domain.model.Category;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CategoryRepositoryPort {
    List<Category> findAll();
    Optional<Category> findById(UUID id);
    Category save(Category category);
    void deleteById(UUID id);
}
