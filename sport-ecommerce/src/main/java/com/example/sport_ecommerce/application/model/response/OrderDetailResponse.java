package com.example.sport_ecommerce.application.model.response;

import lombok.*;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderDetailResponse {
    private UUID orderId;
    private UUID userId;
    private String userName;
    private float totalPrice;
    private List<ConfigurationResponse> items;
}
