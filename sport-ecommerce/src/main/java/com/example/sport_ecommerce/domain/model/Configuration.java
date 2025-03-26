package com.example.sport_ecommerce.domain.model;

import com.example.sport_ecommerce.domain.model.rule.Rule;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.*;

@Setter
@Getter
@NoArgsConstructor
public class Configuration {
    protected UUID id;
    protected Product product;
    protected Map<Part, PartOption> selectedOptions;
    private final Set<Rule> violationRules = new HashSet<>();

    public Configuration(Product product, Map<Part, PartOption> selectedOptions) {
        this.product = product;
        this.selectedOptions = selectedOptions;
    }

    public float getBasePrice() {
        return selectedOptions.values().stream()
                .map(PartOption::getPrice)
                .reduce(0f, Float::sum);
    }

    public void addViolationRules(Rule rule) {
        violationRules.add(rule);
    }
}
