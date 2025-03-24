package com.example.sport_ecommerce.application.model.dto;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class RuleDTO {
    private List<RestrictionRuleDTO> restrictionRules;
    private List<PriceConditionRuleDTO> priceConditionRules;

    public RuleDTO() {
        this.restrictionRules = new ArrayList<>();
        this.priceConditionRules = new ArrayList<>();
    }

    public void addToRestrictionRules(RestrictionRuleDTO restrictionRule) {
        restrictionRules.add(restrictionRule);
    }

    public void addToPriceConditionRules(PriceConditionRuleDTO priceRule) {
        priceConditionRules.add(priceRule);
    }
}
