package com.example.sport_ecommerce.application.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateRuleDTO {
    private List<CreateRestrictionRuleDTO> restrictionRules;
    private List<CreatePriceConditionRuleDTO> priceConditionRules;
}

