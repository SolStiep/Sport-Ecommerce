package com.example.sport_ecommerce.domain.model;

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

    public PresetConfiguration(Product product, Map<Part, PartOption> selectedOptions, String name, Float price, boolean isActive) {
        super(product, selectedOptions);
        this.name = name;
        this.price = price;
        this.isActive = isActive;
    }
}
