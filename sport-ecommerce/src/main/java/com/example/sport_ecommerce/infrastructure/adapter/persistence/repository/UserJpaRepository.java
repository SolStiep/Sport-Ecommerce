package com.example.sport_ecommerce.infrastructure.adapter.persistence.repository;

import com.example.sport_ecommerce.infrastructure.adapter.persistence.jpa.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserJpaRepository extends JpaRepository<UserEntity, UUID> {
    Optional<UserEntity> findByEmail(String email);
    Optional<UserEntity> findByName(String email);
}

