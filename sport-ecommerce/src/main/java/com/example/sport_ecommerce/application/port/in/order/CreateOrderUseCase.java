package com.example.sport_ecommerce.application.port.in.order;

import com.example.sport_ecommerce.application.model.command.CreateOrderCommand;
import com.example.sport_ecommerce.domain.model.Order;


public interface CreateOrderUseCase {
    Order createOrder(CreateOrderCommand command);
}
