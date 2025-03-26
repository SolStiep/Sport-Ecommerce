package com.example.sport_ecommerce.domain.model.strategy;

import com.example.sport_ecommerce.domain.model.ConditionalPrice;
import com.example.sport_ecommerce.domain.model.Configuration;
import com.example.sport_ecommerce.domain.model.PartOption;

public class SimplePriceStrategy implements PriceStrategy {

    @Override
    public float calculatePrice(Configuration config) {
        float basePrice = config.getBasePrice();
        float conditionalPrice = config.getSelectedOptions().values().stream()
                .flatMap(option -> option.getConditionalPrices().stream())
                .filter(cp -> cp.getCondition().isSatisfied(config))
                .map(ConditionalPrice::getPrice)
                .reduce(0f, Float::sum);

        return basePrice + conditionalPrice;
    }
}
