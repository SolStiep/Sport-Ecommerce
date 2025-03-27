package com.example.sport_ecommerce.application.port.out;

import com.example.sport_ecommerce.domain.model.PresetConfiguration;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PresetConfigurationRepositoryPort {
    List<PresetConfiguration> findByProductId(UUID productId);
    List<PresetConfiguration> findAll();
    PresetConfiguration save(PresetConfiguration preset);
    Optional<PresetConfiguration> findById(UUID id);
    void deleteById(UUID id);
}
