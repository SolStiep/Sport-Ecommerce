package com.example.sport_ecommerce.application.model.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateRestrictionRuleDTO {
    private String ifOption;
    private String operator;
    private List<String> targetOptions;
}