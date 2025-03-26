package com.example.sport_ecommerce.domain.model.service;

import com.example.sport_ecommerce.domain.model.Configuration;
import com.example.sport_ecommerce.domain.model.Product;
import com.example.sport_ecommerce.domain.model.rule.Rule;
import com.example.sport_ecommerce.domain.model.strategy.PriceStrategy;
import com.example.sport_ecommerce.domain.model.strategy.SimplePriceStrategy;
import com.example.sport_ecommerce.domain.model.valueobject.PriceStrategyType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Setter
@Getter
@NoArgsConstructor
public class Configurator {
    private UUID id;
    private Product product;
    private List<Rule> rules;
    private PriceStrategy priceStrategy;
    private PriceStrategyType priceStrategyType;

    public Configurator(Product product, List<Rule> rules, PriceStrategyType priceStrategyType) {
        this.product = product;
        this.rules = rules;
        this.priceStrategyType = priceStrategyType;
        this.priceStrategy = setStrategy(priceStrategyType);
    }

    public Configurator(UUID id, Product product, List<Rule> rules, PriceStrategyType priceStrategyType) {
        this.id = id;
        this.product = product;
        this.rules = rules;
        this.priceStrategyType = priceStrategyType;
        this.priceStrategy = setStrategy(priceStrategyType);
    }

    public boolean isValid(Configuration config) {
        rules.forEach(rule -> rule.isValid(config));
        return config.getViolationRules().isEmpty();
    }

    public float calculatePrice(Configuration config) {
        return priceStrategy.calculatePrice(config);
    }

    private PriceStrategy setStrategy(PriceStrategyType strategy) {
        switch (strategy) {
            case SIMPLE:
                return new SimplePriceStrategy();
            // More strategy types can be added here if needed
            // case NEW_TYPE:
            //     return new NewPriceStrategy();
            default:
                return new SimplePriceStrategy();
        }
    }
}
