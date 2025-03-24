package com.example.sport_ecommerce.application.model.dto;

import lombok.*;

import java.util.Map;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class PriceConditionRuleDTO {
    private UUID id;
    @EqualsAndHashCode.Include
    private Map<String, String> requiredOptions;
}
