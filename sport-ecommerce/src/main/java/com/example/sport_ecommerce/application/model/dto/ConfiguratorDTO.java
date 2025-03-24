package com.example.sport_ecommerce.application.model.dto;

import com.example.sport_ecommerce.domain.model.valueobject.PriceStrategyType;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ConfiguratorDTO {
    private RuleDTO rules;
    private PriceStrategyType priceStrategyType;
}
