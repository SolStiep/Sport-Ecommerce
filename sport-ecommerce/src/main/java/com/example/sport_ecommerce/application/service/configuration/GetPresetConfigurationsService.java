package com.example.sport_ecommerce.application.service.configuration;

import com.example.sport_ecommerce.application.mapper.PresetConfigurationResponseMapper;
import com.example.sport_ecommerce.application.model.response.PresetConfigurationResponse;
import com.example.sport_ecommerce.application.port.in.configuration.GetPresetConfigurationsUseCase;
import com.example.sport_ecommerce.application.port.out.PresetConfigurationRepositoryPort;
import com.example.sport_ecommerce.domain.model.PresetConfiguration;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GetPresetConfigurationsService implements GetPresetConfigurationsUseCase {

    private final PresetConfigurationRepositoryPort presetRepository;
    private final PresetConfigurationResponseMapper responseMapper;

    @Override
    public List<PresetConfigurationResponse> getPresetsByProduct(UUID productId) {
        List<PresetConfiguration> presets = presetRepository.findByProductId(productId);
        return responseMapper.toResponseList(presets);
    }
}
