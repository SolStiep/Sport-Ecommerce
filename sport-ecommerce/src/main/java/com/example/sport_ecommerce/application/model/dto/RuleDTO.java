package com.example.sport_ecommerce.application.model.dto;

import com.example.sport_ecommerce.domain.model.valueobject.RuleType;
import lombok.*;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RuleDTO {
    private RuleType type;

    // For RestrictionRule
    private String ifOption;
    private String operator;
    private List<String> targetOptions;

    // For PriceConditionRule
    private Map<String, String> requiredOptions;
}
