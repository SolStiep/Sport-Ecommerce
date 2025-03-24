package com.example.sport_ecommerce.domain.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

    public Configuration(Product product, Map<Part, PartOption> selectedOptions) {
        this.product = product;
        this.selectedOptions = selectedOptions;
    }

    public float getBasePrice() {
        return selectedOptions.values().stream()
                .map(PartOption::getPrice)
                .reduce(0f, Float::sum);
    }
}
