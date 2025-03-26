package com.example.sport_ecommerce.application.model.response;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ConditionalPriceResponse {
    private UUID ruleId;
    private float price;
}
