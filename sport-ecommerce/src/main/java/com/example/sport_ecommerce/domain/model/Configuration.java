package com.example.sport_ecommerce.domain.model;

import com.example.sport_ecommerce.domain.model.rule.PriceConditionRule;
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
    private int quantity;

    public Configuration(Product product, Map<Part, PartOption> selectedOptions, int quantity) {
        this.product = product;
        this.selectedOptions = selectedOptions;
        this.quantity = quantity;
    }

    public float getBasePrice() {
        return selectedOptions.values().stream()
                .map(PartOption::getPrice)
                .reduce(0f, Float::sum);
    }

    public float getConditionalPrice() {
        return selectedOptions.values().stream()
                .flatMap(option -> option.getPriceConditionRules().stream())
                .filter(rule -> rule.isSatisfied(this))
                .map(PriceConditionRule::getPrice)
                .reduce(0f, Float::sum);
    }

    public void addViolationRules(Rule rule) {
        violationRules.add(rule);
    }

    public String getName() {
        return product.getName();
    }

    public boolean isPreset() {
        return false;
    }

}
