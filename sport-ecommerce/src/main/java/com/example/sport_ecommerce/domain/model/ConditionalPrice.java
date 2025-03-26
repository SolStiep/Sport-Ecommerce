package com.example.sport_ecommerce.domain.model;

import com.example.sport_ecommerce.domain.model.rule.Rule;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Setter
@Getter
@NoArgsConstructor
public class ConditionalPrice {
    private UUID id;
    private Rule condition;
    private float price;

    public ConditionalPrice(Rule condition, float price) {
        this.condition = condition;
        this.price = price;
    }

    public ConditionalPrice(UUID id, Rule condition, float price) {
        this.id = id;
        this.condition = condition;
        this.price = price;
    }
}
