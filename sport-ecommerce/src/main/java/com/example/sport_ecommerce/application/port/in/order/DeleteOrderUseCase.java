package com.example.sport_ecommerce.application.port.in.order;

import java.util.UUID;

public interface DeleteOrderUseCase {
    void delete(UUID orderId);
}
