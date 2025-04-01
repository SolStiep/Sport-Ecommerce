package com.example.sport_ecommerce.application.port.out;

import com.example.sport_ecommerce.domain.model.service.Configurator;

public interface ConfiguratorRepositoryPort {
    Configurator save(Configurator configurator);
}
