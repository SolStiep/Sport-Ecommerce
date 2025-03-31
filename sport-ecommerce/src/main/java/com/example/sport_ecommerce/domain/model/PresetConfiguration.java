package com.example.sport_ecommerce.domain.model;

import com.example.sport_ecommerce.domain.model.rule.PriceConditionRule;
import com.example.sport_ecommerce.domain.model.service.Configurator;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;
import java.util.UUID;

@Setter
@Getter
@NoArgsConstructor
public class PresetConfiguration extends Configuration {
    private String name;
    private Float price;
    private boolean isActive;

    public PresetConfiguration(Product product, Map<Part, PartOption> selectedOptions, String name, Float price, boolean isActive, int quantity) {
        super(product, selectedOptions, quantity);
        this.name = name;
        this.price = price;
        this.isActive = isActive;
    }

    @Override
    public float getBasePrice() {
        if (this.price != null) {
            return this.price;
        }
        return super.getBasePrice();
    }

    @Override
    public float getConditionalPrice() {
        if (this.price != null) {
            return 0;
        }
        return super.getConditionalPrice();
    }

    @Override
    public String getName() {
        if (this.name != null) {
            return this.name;
        }
        return super.getName();
    }

    @Override
    public boolean isPreset() {
        return true;
    }
}
