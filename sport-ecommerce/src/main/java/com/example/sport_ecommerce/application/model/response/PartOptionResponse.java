package com.example.sport_ecommerce.application.model.response;

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
}
