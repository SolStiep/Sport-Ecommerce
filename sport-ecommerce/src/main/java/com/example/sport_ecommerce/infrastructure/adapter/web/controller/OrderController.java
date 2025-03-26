package com.example.sport_ecommerce.infrastructure.adapter.web.controller;

import com.example.sport_ecommerce.application.mapper.OrderResponseMapper;
import com.example.sport_ecommerce.application.model.command.CreateOrderCommand;
import com.example.sport_ecommerce.application.model.response.OrderDetailResponse;
import com.example.sport_ecommerce.application.model.response.UserOrderSummaryResponse;
import com.example.sport_ecommerce.application.port.in.order.CreateOrderUseCase;
import com.example.sport_ecommerce.application.port.in.order.GetOrderDetailUseCase;
import com.example.sport_ecommerce.application.port.in.order.GetUserOrdersUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final CreateOrderUseCase createOrderUseCase;
    private final GetOrderDetailUseCase getOrderDetailUseCase;
    private final GetUserOrdersUseCase getUserOrdersUseCase;
    private final OrderResponseMapper orderResponseMapper;

    @PostMapping
    public ResponseEntity<OrderDetailResponse> createOrder(@RequestBody @Valid CreateOrderCommand command) {
        OrderDetailResponse order = createOrderUseCase.createOrder(command);
        return ResponseEntity.status(HttpStatus.CREATED).body(order);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderDetailResponse> getOrderById(@PathVariable UUID id) {
        return ResponseEntity.ok(getOrderDetailUseCase.getOrderById(id));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<UserOrderSummaryResponse>> getOrdersByUser(@PathVariable UUID userId) {
        return ResponseEntity.ok(getUserOrdersUseCase.getOrdersByUser(userId));
    }
}

