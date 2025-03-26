package com.example.sport_ecommerce.domain.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Setter
@Getter
@NoArgsConstructor
public class Part {
    private UUID id;
    private String name;
    private Product product;
    private List<PartOption> options;

    public Part(String name, Product product, List<PartOption> options) {
        this.name = name;
        this.product = product;
        this.options = options;
    }

    public Part(UUID id, String name, Product product, List<PartOption> options) {
        this.id = id;
        this.name = name;
        this.product = product;
        this.options = options;
    }
}
