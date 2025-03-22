package com.example.sport_ecommerce.domain.model.service;

import com.example.sport_ecommerce.domain.model.Order;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class OrderPricingService {
    private final Configurator configurator;

    public OrderPricingService(Configurator configurator) {
        this.configurator = configurator;
    }

    public float getTotalPrice(Order order) {
        return order.getItems().stream()
                .map(configurator::calculatePrice)
                .reduce(0f, Float::sum);
    }
}
