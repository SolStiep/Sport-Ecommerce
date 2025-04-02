package com.example.sport_ecommerce.application.service.order;

import com.example.sport_ecommerce.application.port.in.order.DeleteOrderUseCase;
import com.example.sport_ecommerce.application.port.out.OrderRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DeleteOrderService implements DeleteOrderUseCase {
    private final OrderRepositoryPort orderRepositoryPort;
    
    @Override
    @Transactional
    public void delete(UUID orderId) {
        orderRepositoryPort.deleteById(orderId);
    }
}
