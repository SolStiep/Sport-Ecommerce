package com.example.sport_ecommerce.domain.model.rule;

import com.example.sport_ecommerce.domain.model.Configuration;
import com.example.sport_ecommerce.domain.model.PartOption;
import com.example.sport_ecommerce.domain.model.valueobject.RuleOperator;

import java.util.List;

public class RestrictionRule implements Rule {
    private PartOption ifOption;
    private RuleOperator operator;
    private List<PartOption> targetOptions;

    public RestrictionRule(PartOption ifOption, RuleOperator operator, List<PartOption> targetOptions) {
        this.ifOption = ifOption;
        this.operator = operator;
        this.targetOptions = targetOptions;
    }

    @Override
    public boolean isSatisfied(Configuration config) {
        boolean selected = config.getSelectedOptions().containsValue(ifOption);

        return switch (operator) {
            case REQUIRES -> selected && config.getSelectedOptions().values().containsAll(targetOptions);
            case EXCLUDES -> selected && targetOptions.stream().noneMatch(config.getSelectedOptions().values()::contains);
        };
    }
}
