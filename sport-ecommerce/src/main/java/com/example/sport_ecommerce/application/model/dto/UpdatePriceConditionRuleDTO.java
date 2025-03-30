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
public class UpdatePriceConditionRuleDTO {
    @EqualsAndHashCode.Include
    private UUID ifOption;
    @EqualsAndHashCode.Include
    private List<UUID> requiredOptions;
    private float price;
}