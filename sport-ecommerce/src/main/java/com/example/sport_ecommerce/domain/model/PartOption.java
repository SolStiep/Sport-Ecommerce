package com.example.sport_ecommerce.domain.model;

import com.example.sport_ecommerce.domain.model.rule.PriceConditionRule;
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
    private List<PriceConditionRule> priceConditionRules;

    public PartOption(UUID id) {
        this.id = id;
    }

    public PartOption(String name, float price, boolean inStock, List<PriceConditionRule> priceConditionRules) {
        this.name = name;
        this.price = price;
        this.inStock = inStock;
        this.priceConditionRules = priceConditionRules;
    }

    public PartOption(UUID id, String name, float price, boolean inStock, List<PriceConditionRule> priceConditionRules) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.inStock = inStock;
        this.priceConditionRules = priceConditionRules;
    }

    public void addPriceConditionRule(PriceConditionRule rule) {
        priceConditionRules.add(rule);
    }
}
