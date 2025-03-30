package com.example.sport_ecommerce.infrastructure.adapter.persistence;

import com.example.sport_ecommerce.application.port.out.CategoryRepositoryPort;
import com.example.sport_ecommerce.application.port.out.ConfiguratorRepositoryPort;
import com.example.sport_ecommerce.domain.model.Category;
import com.example.sport_ecommerce.domain.model.Part;
import com.example.sport_ecommerce.domain.model.PartOption;
import com.example.sport_ecommerce.domain.model.rule.Rule;
import com.example.sport_ecommerce.domain.model.service.Configurator;
import com.example.sport_ecommerce.infrastructure.adapter.persistence.jpa.*;
import com.example.sport_ecommerce.infrastructure.adapter.persistence.mapper.CategoryEntityMapper;
import com.example.sport_ecommerce.infrastructure.adapter.persistence.mapper.ConfiguratorEntityMapper;
import com.example.sport_ecommerce.infrastructure.adapter.persistence.mapper.ProductEntityMapper;
import com.example.sport_ecommerce.infrastructure.adapter.persistence.mapper.RuleEntityMapper;
import com.example.sport_ecommerce.infrastructure.adapter.persistence.repository.CategoryJpaRepository;
import com.example.sport_ecommerce.infrastructure.adapter.persistence.repository.ConfiguratorJpaRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ConfiguratorRepositoryAdapter implements ConfiguratorRepositoryPort {

    private final ConfiguratorJpaRepository jpaRepository;
    private final ProductEntityMapper productMapper;
    private final ConfiguratorEntityMapper mapper;
    private final RuleEntityMapper ruleEntityMapper;

    @Override
    @Transactional
    public Configurator save(Configurator configurator) {
        ProductEntity productEntity = productMapper.toEntity(configurator.getProduct());
        List<PartOptionEntity> partOptionEntities = productEntity.getParts().stream()
                .flatMap(part -> part.getOptions().stream())
                .collect(Collectors.toList());

        List<RuleEntity> ruleEntities = configurator.getRules().stream()
                .map(rule -> ruleEntityMapper.toEntity(rule, partOptionEntities))
                .collect(Collectors.toList());

        ConfiguratorEntity configuratorEntity = ConfiguratorEntity.builder()
                .product(productEntity)
                .rules(ruleEntities)
                .priceStrategyType(configurator.getPriceStrategyType())
                .build();
        ruleEntities.forEach(ruleEntity -> ruleEntity.setConfigurator(configuratorEntity));

        ConfiguratorEntity saved = jpaRepository.save(configuratorEntity);
        return mapper.toDomain(saved, partOptionEntities);
    }
}

