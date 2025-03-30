package com.example.sport_ecommerce.application.port.in.configurator;

import com.example.sport_ecommerce.application.model.dto.ConfiguratorDTO;
import com.example.sport_ecommerce.application.model.response.ConfiguratorResponse;


public interface ManageConfiguratorUseCase {
    ConfiguratorResponse create(ConfiguratorDTO command);
}
