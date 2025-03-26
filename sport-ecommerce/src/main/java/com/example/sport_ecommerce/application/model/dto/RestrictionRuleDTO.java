package com.example.sport_ecommerce.application.model.dto;

import lombok.*;

import java.util.List;
import java.util.UUID;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RestrictionRuleDTO {
    private UUID id;
    private UUID ifOption;
    private String operator;
    private List<UUID> targetOptions;
}
