package com.example.sport_ecommerce.application.port.in.configuration;

import com.example.sport_ecommerce.application.model.dto.ConfigurationDTO;
import com.example.sport_ecommerce.application.model.response.PrepareConfigurationResponse;

public interface PrepareConfigurationUseCase {
    PrepareConfigurationResponse prepare(ConfigurationDTO configurationDTO);
}
