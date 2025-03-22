package com.example.sport_ecommerce.domain.model.strategy;

import com.example.sport_ecommerce.domain.model.Configuration;

public interface PriceStrategy {
    float calculatePrice(Configuration config);
}
