package com.example.sport_ecommerce.domain.model.rule;

import com.example.sport_ecommerce.domain.model.Configuration;
import com.example.sport_ecommerce.domain.model.PartOption;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Setter
@Getter
@NoArgsConstructor
public class PriceConditionRule implements Rule {
    private UUID id;
    private PartOption ifOption;
    private List<PartOption> requiredOptions;

    public PriceConditionRule(PartOption ifOption, List<PartOption> requiredOptions) {
        this.ifOption = ifOption;
        this.requiredOptions = requiredOptions;
    }

    @Override
    public void accept(RuleVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public boolean isSatisfied(Configuration config) {
        boolean ifOptionSelected = config.getSelectedOptions().values().stream()
                .anyMatch(opt -> opt.getId().equals(ifOption.getId()));

        if (!ifOptionSelected) return true;

        boolean satisfied = requiredOptions.stream()
                .anyMatch(required -> config.getSelectedOptions().values().stream()
                        .anyMatch(opt -> opt.getId().equals(required.getId())));

        if (satisfied) {
            config.addViolationRules(this);
        }

        return satisfied;
    }

    @Override
    public boolean isValid(Configuration config) {
        return true;
    }
}
