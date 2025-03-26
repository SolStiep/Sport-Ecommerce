package com.example.sport_ecommerce.integration;

import com.example.sport_ecommerce.application.model.dto.ConfigurationDTO;
import com.example.sport_ecommerce.application.model.response.PrepareConfigurationResponse;
import com.example.sport_ecommerce.application.port.in.configuration.PrepareConfigurationUseCase;
import com.example.sport_ecommerce.domain.model.*;
import com.example.sport_ecommerce.domain.model.rule.PriceConditionRule;
import com.example.sport_ecommerce.domain.model.rule.RestrictionRule;
import com.example.sport_ecommerce.domain.model.service.Configurator;
import com.example.sport_ecommerce.domain.model.valueobject.PriceStrategyType;
import com.example.sport_ecommerce.domain.model.valueobject.RuleOperator;
import com.example.sport_ecommerce.infrastructure.adapter.persistence.jpa.CategoryEntity;
import com.example.sport_ecommerce.infrastructure.adapter.persistence.jpa.ProductEntity;
import com.example.sport_ecommerce.infrastructure.adapter.persistence.mapper.CategoryEntityMapper;
import com.example.sport_ecommerce.infrastructure.adapter.persistence.mapper.ProductEntityMapper;
import com.example.sport_ecommerce.infrastructure.adapter.persistence.repository.CategoryJpaRepository;
import com.example.sport_ecommerce.infrastructure.adapter.persistence.repository.ProductJpaRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
public class ConfigurationIntegrationTest {

    @Autowired
    private PrepareConfigurationUseCase useCase;

    @Autowired
    private ProductJpaRepository productJpaRepository;

    @Autowired
    private CategoryJpaRepository categoryJpaRepository;

    @Autowired
    private ProductEntityMapper productMapper;

    @Autowired
    private CategoryEntityMapper categoryMapper;

    @Test
    void should_be_valid_and_include_conditional_price() {
        var productHelper = TestDataHelper.persistSampleProductWithRules(
                productJpaRepository, categoryJpaRepository, productMapper, categoryMapper
        );

        // Act: Build ConfigurationDTO with UUIDs
        Product product = productHelper.domain();
        UUID productId = productHelper.productId();

        ConfigurationDTO configDTO = getConfigurationDTO(product, productId);

        // Act
        PrepareConfigurationResponse result = useCase.prepare(configDTO);

        // Assert
        assertThat(result.isValid()).isTrue();
        assertThat(result.getTotalPrice()).isEqualTo(353.0f); // 343 base + 10 conditional
    }

    private ConfigurationDTO getConfigurationDTO(Product product, UUID productId) {
        Map<String, UUID> partIds = new HashMap<>();
        Map<String, UUID> optionIds = new HashMap<>();

        for (Part part : product.getParts()) {
            partIds.put(part.getName(), part.getId());
            for (PartOption opt : part.getOptions()) {
                optionIds.put(opt.getName(), opt.getId());
            }
        }

        Map<UUID, UUID> selectedOptions = Map.of(
                partIds.get("Frame Type"), optionIds.get("Full-suspension"),
                partIds.get("Rim Color"), optionIds.get("Blue"),
                partIds.get("Frame Finish"), optionIds.get("Matte"),
                partIds.get("Wheels"), optionIds.get("Mountain wheels"),
                partIds.get("Chain"), optionIds.get("Single-speed chain")
        );

        return new ConfigurationDTO(productId, selectedOptions);
    }
}
