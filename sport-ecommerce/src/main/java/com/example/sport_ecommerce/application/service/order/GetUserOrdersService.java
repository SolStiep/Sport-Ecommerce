package com.example.sport_ecommerce.application.service.order;

import com.example.sport_ecommerce.application.mapper.OrderResponseMapper;
import com.example.sport_ecommerce.application.model.response.UserOrderSummaryResponse;
import com.example.sport_ecommerce.application.port.in.order.GetUserOrdersUseCase;
import com.example.sport_ecommerce.application.port.out.OrderRepositoryPort;
import com.example.sport_ecommerce.domain.model.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GetUserOrdersService implements GetUserOrdersUseCase {

    private final OrderRepositoryPort orderRepository;
    private final OrderResponseMapper responseMapper;

    @Override
    public List<UserOrderSummaryResponse> getOrdersByUser(UUID userId) {
        List<Order> orders = orderRepository.findByUserId(userId);
        return responseMapper.toSummaryList(orders);
    }
}
