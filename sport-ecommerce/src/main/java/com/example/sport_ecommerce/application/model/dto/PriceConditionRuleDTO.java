package com.example.sport_ecommerce.application.model.dto;

import lombok.*;

import java.util.List;
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
    private String ifOption;
    @EqualsAndHashCode.Include
    private List<String> requiredOptions;
}
