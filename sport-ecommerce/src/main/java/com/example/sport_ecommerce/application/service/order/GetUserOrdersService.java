package com.example.sport_ecommerce.application.service.order;

import com.example.sport_ecommerce.application.mapper.OrderResponseMapper;
import com.example.sport_ecommerce.application.model.response.OrderDetailResponse;
import com.example.sport_ecommerce.application.model.response.UserOrderSummaryResponse;
import com.example.sport_ecommerce.application.port.in.order.GetUserOrdersUseCase;
import com.example.sport_ecommerce.application.port.out.OrderRepositoryPort;
import com.example.sport_ecommerce.application.port.out.UserRepositoryPort;
import com.example.sport_ecommerce.domain.model.Order;
import com.example.sport_ecommerce.domain.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GetUserOrdersService implements GetUserOrdersUseCase {

    private final OrderRepositoryPort orderRepository;
    private final UserRepositoryPort userRepository;
    private final OrderResponseMapper responseMapper;

    @Override
    public List<OrderDetailResponse> getOrdersByUser() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with email: " + email));
        List<Order> orders = orderRepository.findByUserId(user.getId());
        return responseMapper.toDetailList(orders);
    }
}
