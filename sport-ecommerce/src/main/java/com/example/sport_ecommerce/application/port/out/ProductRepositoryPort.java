package com.example.sport_ecommerce.application.port.out;

import com.example.sport_ecommerce.domain.model.Product;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProductRepositoryPort {
    List<Product> findAll();
    Optional<Product> findById(UUID id);
    Product save(Product product);
    void deleteById(UUID id);
}
