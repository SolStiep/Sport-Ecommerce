package com.example.sport_ecommerce.application.service.order;

import com.example.sport_ecommerce.application.mapper.ConfigurationMapper;
import com.example.sport_ecommerce.application.mapper.OrderResponseMapper;
import com.example.sport_ecommerce.application.model.command.CreateOrderCommand;
import com.example.sport_ecommerce.application.model.response.OrderDetailResponse;
import com.example.sport_ecommerce.application.port.in.order.CreateOrderUseCase;
import com.example.sport_ecommerce.application.port.out.OrderRepositoryPort;
import com.example.sport_ecommerce.application.port.out.UserRepositoryPort;
import com.example.sport_ecommerce.domain.model.Configuration;
import com.example.sport_ecommerce.domain.model.Order;
import com.example.sport_ecommerce.domain.model.User;
import com.example.sport_ecommerce.domain.model.service.Configurator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CreateOrderService implements CreateOrderUseCase {

    private final OrderRepositoryPort orderRepository;
    private final UserRepositoryPort userRepository;
    private final ConfigurationMapper configurationMapper;
    private final OrderResponseMapper responseMapper;

    @Override
    public OrderDetailResponse createOrder(CreateOrderCommand command) {
        User user = userRepository.findById(command.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("User not found."));

        List<Configuration> items = configurationMapper.toDomainList(command.getItems());

        float total = items.stream()
                .map(config -> {
                    Configurator configurator = config.getProduct().getConfigurator();
                    return configurator.calculatePrice(config);
                })
                .reduce(0f, Float::sum);

        Order order = new Order(user, items, total);

        Order saved = orderRepository.save(order);
        return responseMapper.toDetail(saved);
    }
}
