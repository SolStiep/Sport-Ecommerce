package com.example.sport_ecommerce.domain.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Setter
@Getter
@NoArgsConstructor
public class PartOption {
    private UUID id;
    private String name;
    private float price;
    private boolean inStock;
    private List<ConditionalPrice> conditionalPrices;

    public PartOption(String name, float price, boolean inStock, List<ConditionalPrice> conditionalPrices) {
        this.name = name;
        this.price = price;
        this.inStock = inStock;
        this.conditionalPrices = conditionalPrices;
    }

    public PartOption(UUID id, String name, float price, boolean inStock, List<ConditionalPrice> conditionalPrices) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.inStock = inStock;
        this.conditionalPrices = conditionalPrices;
    }
}
