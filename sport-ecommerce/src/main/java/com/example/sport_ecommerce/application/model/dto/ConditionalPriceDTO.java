package com.example.sport_ecommerce.application.model.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ConditionalPriceDTO {
    private PriceConditionRuleDTO rule;
    private float price;
}
