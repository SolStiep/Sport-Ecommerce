package com.example.sport_ecommerce.application.mapper;

import com.example.sport_ecommerce.application.model.response.OrderDetailResponse;
import com.example.sport_ecommerce.application.model.response.UserOrderSummaryResponse;
import com.example.sport_ecommerce.domain.model.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class OrderResponseMapper {
    private final ConfigurationResponseMapper configurationMapper;

    public UserOrderSummaryResponse toSummary(Order order) {
        return UserOrderSummaryResponse.builder()
                .orderId(order.getId())
                .totalPrice(order.getTotalPrice())
                .build();
    }

    public List<UserOrderSummaryResponse> toSummaryList(List<Order> orders) {
        return orders.stream().map(this::toSummary).toList();
    }

    public OrderDetailResponse toDetail(Order order) {
        return OrderDetailResponse.builder()
                .orderId(order.getId())
                .userId(order.getUser().getId())
                .userName(order.getUser().getName())
                .totalPrice(order.getTotalPrice())
                .items(configurationMapper.toResponseList(order.getItems()))
                .build();
    }
}
