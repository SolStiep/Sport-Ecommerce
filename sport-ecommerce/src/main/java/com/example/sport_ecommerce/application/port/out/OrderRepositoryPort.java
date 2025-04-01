package com.example.sport_ecommerce.application.port.out;

import com.example.sport_ecommerce.domain.model.Order;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface OrderRepositoryPort {
    Order save(Order order);
    Optional<Order> findById(UUID id);
    List<Order> findByUserId(UUID userId);
    void deleteById(UUID orderId);
}
