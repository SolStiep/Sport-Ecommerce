package com.example.sport_ecommerce.domain.model.strategy;

import com.example.sport_ecommerce.domain.model.ConditionalPrice;
import com.example.sport_ecommerce.domain.model.Configuration;
import com.example.sport_ecommerce.domain.model.PartOption;

public class SimplePriceStrategy implements PriceStrategy {

    @Override
    public float calculatePrice(Configuration config) {
        float basePrice = config.getBasePrice();
        float conditionalPrice = 0f;

        for (PartOption option : config.getSelectedOptions().values()) {
            for (ConditionalPrice condPrice : option.getConditionalPrices()) {
                if (condPrice.getCondition().isSatisfied(config)) {
                    conditionalPrice += condPrice.getPrice();
                }
            }
        }

        return basePrice + conditionalPrice;
    }
}
