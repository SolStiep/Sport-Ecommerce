package com.example.sport_ecommerce.application.model.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class CreatePriceConditionRuleDTO {
    @EqualsAndHashCode.Include
    private String ifOption;
    @EqualsAndHashCode.Include
    private List<String> requiredOptions;
}