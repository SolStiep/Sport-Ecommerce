package com.example.sport_ecommerce.domain.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Setter
@Getter
@NoArgsConstructor
public class Product {
    private UUID id;
    private String name;
    private Category category;
    private List<Part> parts;

    public Product(String name, Category category, List<Part> parts) {
        this.name = name;
        this.category = category;
        this.parts = parts;
    }
}
