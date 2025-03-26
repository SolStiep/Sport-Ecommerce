package com.example.sport_ecommerce.infrastructure.adapter.persistence.repository;

import com.example.sport_ecommerce.infrastructure.adapter.persistence.jpa.PresetConfigurationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface PresetConfigurationJpaRepository extends JpaRepository<PresetConfigurationEntity, UUID> {
    List<PresetConfigurationEntity> findByProductId(UUID productId);
}

