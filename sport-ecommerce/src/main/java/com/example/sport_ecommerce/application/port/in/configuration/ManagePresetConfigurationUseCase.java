package com.example.sport_ecommerce.application.port.in.configuration;

import com.example.sport_ecommerce.application.model.command.CreatePresetConfigurationCommand;
import com.example.sport_ecommerce.domain.model.PresetConfiguration;

import java.util.UUID;

public interface ManagePresetConfigurationUseCase {
    PresetConfiguration create(CreatePresetConfigurationCommand command);
    PresetConfiguration update(UUID presetId, CreatePresetConfigurationCommand command);
    void delete(UUID presetId);
}
