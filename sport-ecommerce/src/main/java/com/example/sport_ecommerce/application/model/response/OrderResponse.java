package com.example.sport_ecommerce.application.model.response;

import lombok.*;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderResponse {
    private UUID id;
    private UUID userId;
    private float totalPrice;
    private List<ConfigurationResponse> items;
}
