package com.example.sport_ecommerce.application.port.in.configuration;

import com.example.sport_ecommerce.application.model.response.PresetCatalogResponse;
import com.example.sport_ecommerce.application.model.response.PresetConfigurationResponse;
import com.example.sport_ecommerce.domain.model.PresetConfiguration;

import java.util.List;
import java.util.UUID;

public interface GetPresetConfigurationsUseCase {
    List<PresetConfigurationResponse> getPresetsByProduct(UUID productId);
    List<PresetCatalogResponse> getAllPresets();
}
