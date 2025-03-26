package com.example.sport_ecommerce.application.model.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PartOptionDTO {
    private String name;
    private float price;
    private boolean inStock;
    private List<ConditionalPriceDTO> conditionalPrices;
}
