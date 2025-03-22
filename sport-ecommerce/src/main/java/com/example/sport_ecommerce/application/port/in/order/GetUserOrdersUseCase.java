package com.example.sport_ecommerce.application.port.in.order;

import com.example.sport_ecommerce.application.model.response.UserOrderSummaryResponse;

import java.util.List;
import java.util.UUID;

public interface GetUserOrdersUseCase {
    List<UserOrderSummaryResponse> getOrdersByUser(UUID userId);
}
