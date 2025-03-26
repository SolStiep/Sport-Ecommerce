package com.example.sport_ecommerce.application.service.configuration;

import com.example.sport_ecommerce.application.mapper.ConfigurationMapper;
import com.example.sport_ecommerce.application.mapper.PrepareConfigurationResponseMapper;
import com.example.sport_ecommerce.application.model.dto.ConfigurationDTO;
import com.example.sport_ecommerce.application.model.response.PrepareConfigurationResponse;
import com.example.sport_ecommerce.application.port.in.configuration.PrepareConfigurationUseCase;
import com.example.sport_ecommerce.domain.model.Configuration;
import com.example.sport_ecommerce.domain.model.service.Configurator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PrepareConfigurationService implements PrepareConfigurationUseCase {
    private final ConfigurationMapper configurationMapper;
    private final PrepareConfigurationResponseMapper configurationResponseMapper;

    @Override
    public PrepareConfigurationResponse prepare(ConfigurationDTO configurationDTO) {
        Configuration config = configurationMapper.toDomain(configurationDTO);

        Configurator configurator = config.getProduct().getConfigurator();
        boolean valid = configurator.isValid(config);
        float price = configurator.calculatePrice(config);

        return configurationResponseMapper.toResponse(config, price, valid);
    }
}
