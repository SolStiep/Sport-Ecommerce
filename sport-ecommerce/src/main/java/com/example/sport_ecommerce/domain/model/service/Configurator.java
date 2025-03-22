package com.example.sport_ecommerce.domain.model.service;

import com.example.sport_ecommerce.domain.model.Configuration;
import com.example.sport_ecommerce.domain.model.Product;
import com.example.sport_ecommerce.domain.model.rule.Rule;
import com.example.sport_ecommerce.domain.model.strategy.PriceStrategy;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
public class Configurator {
    private Product product;
    private List<Rule> rules;
    private PriceStrategy priceStrategy;

    public Configurator(Product product, List<Rule> rules, PriceStrategy priceStrategy) {
        this.product = product;
        this.rules = rules;
        this.priceStrategy = priceStrategy;
    }

    public boolean isValid(Configuration config) {
        return rules.stream().allMatch(rule -> rule.isSatisfied(config));
    }

    public float calculatePrice(Configuration config) {
        return priceStrategy.calculatePrice(config);
    }
}
