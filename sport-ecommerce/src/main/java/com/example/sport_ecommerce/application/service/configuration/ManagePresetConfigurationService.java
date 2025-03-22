package com.example.sport_ecommerce.application.service.configuration;

import com.example.sport_ecommerce.application.mapper.PresetConfigurationMapper;
import com.example.sport_ecommerce.application.model.command.CreatePresetConfigurationCommand;
import com.example.sport_ecommerce.application.port.in.configuration.ManagePresetConfigurationUseCase;
import com.example.sport_ecommerce.application.port.out.PresetConfigurationRepositoryPort;
import com.example.sport_ecommerce.domain.model.PresetConfiguration;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ManagePresetConfigurationService implements ManagePresetConfigurationUseCase {

    private final PresetConfigurationRepositoryPort presetRepository;
    private final PresetConfigurationMapper presetMapper;

    @Override
    public PresetConfiguration create(CreatePresetConfigurationCommand command) {
        PresetConfiguration preset = presetMapper.toDomain(command);
        return presetRepository.save(preset);
    }

    @Override
    public PresetConfiguration update(UUID presetId, CreatePresetConfigurationCommand command) {
        PresetConfiguration preset = presetMapper.toDomain(command);
        preset.setId(presetId);
        return presetRepository.save(preset);
    }

    @Override
    public void delete(UUID presetId) {
        presetRepository.deleteById(presetId);
    }
}
