package com.example.sport_ecommerce.application.model.response;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserOrderSummaryResponse {
    private UUID orderId;
    private float totalPrice;
}
