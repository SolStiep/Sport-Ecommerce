package com.example.sport_ecommerce.application.model.response;

import com.example.sport_ecommerce.infrastructure.adapter.persistence.jpa.ConditionalPriceEntity;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PartOptionResponse {
    private UUID id;
    private String name;
    private float price;
    private boolean inStock;
    private List<ConditionalPriceResponse> conditionalPrices;
}
