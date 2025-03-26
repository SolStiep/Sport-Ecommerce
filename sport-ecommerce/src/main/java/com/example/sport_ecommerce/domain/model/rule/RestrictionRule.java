package com.example.sport_ecommerce.domain.model.rule;

import com.example.sport_ecommerce.domain.model.Configuration;
import com.example.sport_ecommerce.domain.model.PartOption;
import com.example.sport_ecommerce.domain.model.valueobject.RuleOperator;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Setter
@Getter
@NoArgsConstructor
public class RestrictionRule implements Rule {
    private UUID id;
    private PartOption ifOption;
    private RuleOperator operator;
    private List<PartOption> targetOptions;

    public RestrictionRule(PartOption ifOption, RuleOperator operator, List<PartOption> targetOptions) {
        this.ifOption = ifOption;
        this.operator = operator;
        this.targetOptions = targetOptions;
    }

    @Override
    public void accept(RuleVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public boolean isSatisfied(Configuration config) {
        boolean selected = config.getSelectedOptions().values().stream()
                .anyMatch(opt -> opt.getId().equals(ifOption.getId()));

        return switch (operator) {
            case REQUIRES -> selected &&
                    targetOptions.stream().allMatch(target ->
                            config.getSelectedOptions().values().stream()
                                    .anyMatch(opt -> opt.getId().equals(target.getId()))
                    );
            case EXCLUDES -> selected &&
                    targetOptions.stream().noneMatch(target ->
                            config.getSelectedOptions().values().stream()
                                    .anyMatch(opt -> opt.getId().equals(target.getId()))
                    );
        };
    }

    @Override
    public boolean isValid(Configuration config) {
        return isSatisfied(config);
    }

}
