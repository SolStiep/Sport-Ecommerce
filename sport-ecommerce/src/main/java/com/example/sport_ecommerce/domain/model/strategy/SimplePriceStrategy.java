package com.example.sport_ecommerce.domain.model.strategy;

import com.example.sport_ecommerce.domain.model.Configuration;
import com.example.sport_ecommerce.domain.model.rule.PriceConditionRule;

public class SimplePriceStrategy implements PriceStrategy {

    @Override
    public float calculatePrice(Configuration config) {
        float basePrice = config.getBasePrice();
        float conditionalPrice = config.getConditionalPrice();

        return (basePrice + conditionalPrice)*config.getQuantity();
    }
}
