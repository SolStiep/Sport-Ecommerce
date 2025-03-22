package com.example.sport_ecommerce.domain.model.strategy;

import com.example.sport_ecommerce.domain.model.ConditionalPrice;
import com.example.sport_ecommerce.domain.model.Configuration;

public class SimplePriceStrategy implements PriceStrategy {

    @Override
    public float calculatePrice(Configuration config) {
        float base = config.getBasePrice();
        float cond = config.getApplicableConditionalPrices().stream()
                .map(ConditionalPrice::getPrice)
                .reduce(0f, Float::sum);
        return base + cond;
    }
}
