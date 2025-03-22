package com.example.sport_ecommerce.application.port.in.order;

import com.example.sport_ecommerce.application.model.response.OrderDetailResponse;

import java.util.UUID;

public interface GetOrderDetailUseCase {
    OrderDetailResponse getOrderById(UUID orderId);
}
