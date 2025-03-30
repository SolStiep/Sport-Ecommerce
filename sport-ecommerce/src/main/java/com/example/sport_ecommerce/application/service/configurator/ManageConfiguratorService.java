package com.example.sport_ecommerce.application.service.configurator;

import com.example.sport_ecommerce.application.mapper.ConfiguratorCommandMapper;
import com.example.sport_ecommerce.application.mapper.ConfiguratorResponseMapper;
import com.example.sport_ecommerce.application.model.dto.ConfiguratorDTO;
import com.example.sport_ecommerce.application.model.response.ConfiguratorResponse;
import com.example.sport_ecommerce.application.port.in.configurator.ManageConfiguratorUseCase;
import com.example.sport_ecommerce.application.port.out.ConfiguratorRepositoryPort;
import com.example.sport_ecommerce.application.port.out.ProductRepositoryPort;
import com.example.sport_ecommerce.domain.model.Product;
import com.example.sport_ecommerce.domain.model.service.Configurator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ManageConfiguratorService implements ManageConfiguratorUseCase {

    private final ConfiguratorRepositoryPort configuratorRepository;
    private final ProductRepositoryPort productRepository;
    private final ConfiguratorCommandMapper configuratorMapper;
    private final ConfiguratorResponseMapper responseMapper;

    public ConfiguratorResponse create(ConfiguratorDTO configuratorDTO) {
        Product product = productRepository.findById(configuratorDTO.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found"));
        Configurator configurator = configuratorMapper.toDomain(configuratorDTO, product);
        Configurator saved = configuratorRepository.save(configurator);
        return responseMapper.toResponse(saved);
    }
}
