package com.example.sport_ecommerce.application.port.out;

import com.example.sport_ecommerce.domain.model.User;

import java.util.Optional;
import java.util.UUID;

public interface UserRepositoryPort {
    Optional<User> findById(UUID id);
    Optional<User> findByEmail(String email);
    User save(User user);
}
