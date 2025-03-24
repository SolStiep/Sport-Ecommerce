package com.example.sport_ecommerce.domain.model;

import com.example.sport_ecommerce.domain.model.service.Configurator;
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
    private String description;
    private Category category;
    private List<Part> parts;
    private Configurator configurator;

    public Product(UUID id, String name, String description, Category category, List<Part> parts, Configurator configurator) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.category = category;
        this.parts = parts;
        this.configurator = configurator;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", parts=" + parts +
                ", configurator=" + configurator +
                '}';
    }

}
