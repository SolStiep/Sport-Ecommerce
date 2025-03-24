package com.example.sport_ecommerce.infrastructure.adapter.persistence;

import com.example.sport_ecommerce.application.port.out.UserRepositoryPort;
import com.example.sport_ecommerce.domain.model.User;
import com.example.sport_ecommerce.infrastructure.adapter.persistence.jpa.UserEntity;
import com.example.sport_ecommerce.infrastructure.adapter.persistence.mapper.UserEntityMapper;
import com.example.sport_ecommerce.infrastructure.adapter.persistence.repository.UserJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class UserRepositoryAdapter implements UserRepositoryPort {

    private final UserJpaRepository jpaRepository;
    private final UserEntityMapper mapper;

    @Override
    public User save(User user) {
        UserEntity entity = mapper.toEntity(user);
        UserEntity saved = jpaRepository.save(entity);
        return mapper.toDomain(saved);
    }

    @Override
    public Optional<User> findById(UUID id) {
        return jpaRepository.findById(id).map(mapper::toDomain);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return jpaRepository.findByEmail(email).map(mapper::toDomain);
    }
}

