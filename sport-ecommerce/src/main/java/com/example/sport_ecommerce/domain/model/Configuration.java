package com.example.sport_ecommerce.domain.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Setter
@Getter
@NoArgsConstructor
public class Configuration {
    protected UUID id;
    protected Product product;
    protected Map<Part, PartOption> selectedOptions;

    public Configuration(UUID id, Product product, Map<Part, PartOption> selectedOptions) {
        this.id = id;
        this.product = product;
        this.selectedOptions = selectedOptions;
    }

    public float getBasePrice() {
        return selectedOptions.values().stream()
                .map(PartOption::getPrice)
                .reduce(0f, Float::sum);
    }

    public List<ConditionalPrice> getApplicableConditionalPrices() {
        return selectedOptions.values().stream()
                .flatMap(o -> o.getConditionalPrices().stream())
                .toList();
    }

    public boolean isValid() {
        return product.getConfigurator().isValid(this);
    }

    public float calculatePrice() {
        return product.getConfigurator().calculatePrice(this);
    }
}
