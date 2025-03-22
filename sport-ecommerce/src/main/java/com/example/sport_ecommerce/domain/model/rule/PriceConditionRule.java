package com.example.sport_ecommerce.domain.model.rule;

import com.example.sport_ecommerce.domain.model.Configuration;
import com.example.sport_ecommerce.domain.model.Part;
import com.example.sport_ecommerce.domain.model.PartOption;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;

@Setter
@Getter
@NoArgsConstructor
public class PriceConditionRule implements Rule {
    private Map<Part, PartOption> requiredOptions;

    public PriceConditionRule(Map<Part, PartOption> requiredOptions) {
        this.requiredOptions = requiredOptions;
    }

    @Override
    public boolean isSatisfied(Configuration config) {
        return requiredOptions.entrySet().stream()
                .allMatch(e -> e.getValue().equals(config.getSelectedOptions().get(e.getKey())));
    }
}
