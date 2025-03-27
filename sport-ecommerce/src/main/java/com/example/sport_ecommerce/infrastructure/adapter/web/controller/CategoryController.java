package com.example.sport_ecommerce.infrastructure.adapter.web.controller;

import com.example.sport_ecommerce.application.mapper.CategoryMapper;
import com.example.sport_ecommerce.application.model.command.CategoryCommand;
import com.example.sport_ecommerce.application.model.response.CategoryResponse;
import com.example.sport_ecommerce.application.port.in.category.ManageCategoryUseCase;
import com.example.sport_ecommerce.domain.model.Category;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final ManageCategoryUseCase manageUseCase;
    private final CategoryMapper categoryMapper;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<CategoryResponse> create(@RequestBody @Valid CategoryCommand command) {
        CategoryResponse category = manageUseCase.create(command);
        return ResponseEntity.status(HttpStatus.CREATED).body(category);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<CategoryResponse> update(@PathVariable UUID id, @RequestBody @Valid CategoryCommand command) {
        command.setId(id);
        CategoryResponse category = manageUseCase.update(command);
        return ResponseEntity.ok(category);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        manageUseCase.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<CategoryResponse>> getAll() {
        return ResponseEntity.ok(manageUseCase.getAll());
    }
}

