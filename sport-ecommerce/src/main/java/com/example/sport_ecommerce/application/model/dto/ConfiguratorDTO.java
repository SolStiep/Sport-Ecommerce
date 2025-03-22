package com.example.sport_ecommerce.application.model.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ConfiguratorDTO {
    private List<RuleDTO> rules;
    private String priceStrategy;
}
