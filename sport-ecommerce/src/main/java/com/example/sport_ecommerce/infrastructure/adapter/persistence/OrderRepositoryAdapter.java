package com.example.sport_ecommerce.infrastructure.adapter.persistence;

import com.example.sport_ecommerce.application.port.out.OrderRepositoryPort;
import com.example.sport_ecommerce.application.port.out.ProductRepositoryPort;
import com.example.sport_ecommerce.application.port.out.UserRepositoryPort;
import com.example.sport_ecommerce.domain.model.Order;
import com.example.sport_ecommerce.domain.model.Product;
import com.example.sport_ecommerce.infrastructure.adapter.persistence.jpa.OrderEntity;
import com.example.sport_ecommerce.infrastructure.adapter.persistence.jpa.UserEntity;
import com.example.sport_ecommerce.infrastructure.adapter.persistence.mapper.OrderEntityMapper;
import com.example.sport_ecommerce.infrastructure.adapter.persistence.mapper.ProductResolver;
import com.example.sport_ecommerce.infrastructure.adapter.persistence.repository.OrderJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class OrderRepositoryAdapter implements OrderRepositoryPort, ProductResolver {

    private final OrderJpaRepository jpaRepository;
    private final OrderEntityMapper mapper;
    private final UserRepositoryPort userRepository;
    private final ProductRepositoryPort productRepository;

    @Override
    public Order save(Order order) {
        OrderEntity entity = mapper.toEntity(order);
        entity.setId(order.getId());
        entity.setUser(UserEntity.builder().id(order.getUser().getId()).build());
        entity.setItems(mapper.toEmbeddables(order.getItems()));

        OrderEntity saved = jpaRepository.save(entity);

        Order mapped = mapper.toDomain(saved);
        mapped.setUser(order.getUser());
        mapped.setItems(mapper.toConfigurations(saved.getItems(), this));
        return mapped;
    }

    @Override
    public Optional<Order> findById(UUID id) {
        return jpaRepository.findById(id).map(entity -> {
            Order order = mapper.toDomain(entity);
            order.setItems(mapper.toConfigurations(entity.getItems(), this));
            return order;
        });
    }

    @Override
    public List<Order> findByUserId(UUID userId) {
        return jpaRepository.findByUserId(userId).stream().map(entity -> {
            Order order = mapper.toDomain(entity);
            order.setItems(mapper.toConfigurations(entity.getItems(), this));
            return order;
        }).toList();
    }

    @Override
    public Product resolveProduct(UUID productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found: " + productId));
    }
}
