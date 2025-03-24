package com.example.sport_ecommerce.application.model.response;

import com.example.sport_ecommerce.application.model.dto.RuleDTO;
import com.example.sport_ecommerce.domain.model.valueobject.PriceStrategyType;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ConfiguratorResponse {
    private UUID id;
    private RuleDTO rules;
    private PriceStrategyType priceStrategyType;
}
