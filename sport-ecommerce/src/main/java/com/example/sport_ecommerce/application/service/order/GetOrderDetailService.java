package com.example.sport_ecommerce.application.service.order;

import com.example.sport_ecommerce.application.mapper.OrderResponseMapper;
import com.example.sport_ecommerce.application.model.response.OrderDetailResponse;
import com.example.sport_ecommerce.application.port.in.order.GetOrderDetailUseCase;
import com.example.sport_ecommerce.application.port.out.OrderRepositoryPort;
import com.example.sport_ecommerce.domain.model.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GetOrderDetailService implements GetOrderDetailUseCase {

    private final OrderRepositoryPort orderRepository;
    private final OrderResponseMapper responseMapper;

    @Override
    public OrderDetailResponse getOrderById(UUID orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found."));
        return responseMapper.toDetail(order);
    }
}
