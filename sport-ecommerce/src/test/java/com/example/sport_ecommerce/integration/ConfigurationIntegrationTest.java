package com.example.sport_ecommerce.integration;

import com.example.sport_ecommerce.application.model.dto.ConfigurationDTO;
import com.example.sport_ecommerce.application.model.response.PrepareConfigurationResponse;
import com.example.sport_ecommerce.application.port.in.configuration.PrepareConfigurationUseCase;
import com.example.sport_ecommerce.application.port.out.ConfiguratorRepositoryPort;
import com.example.sport_ecommerce.application.port.out.ProductRepositoryPort;
import com.example.sport_ecommerce.domain.model.Part;
import com.example.sport_ecommerce.domain.model.PartOption;
import com.example.sport_ecommerce.domain.model.Product;
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

    @Autowired private PrepareConfigurationUseCase useCase;
    @Autowired private ProductJpaRepository productJpaRepository;
    @Autowired private CategoryJpaRepository categoryJpaRepository;
    @Autowired private ProductEntityMapper productMapper;
    @Autowired private CategoryEntityMapper categoryMapper;
    @Autowired private ProductRepositoryPort productRepositoryPort;
    @Autowired private ConfiguratorRepositoryPort configuratorRepository;

    @Test
    void should_calculate_price_including_price_condition_rule_when_configuration_is_valid() {
        var productHelper = TestDataHelper.persistSampleProductWithRules(
                productRepositoryPort, categoryJpaRepository, productMapper, categoryMapper, configuratorRepository
        );

        Product product = productHelper.domain();
        UUID productId = productHelper.productId();

        ConfigTestData configData = getConfigurationTestData(product, productId);
        PrepareConfigurationResponse result = useCase.prepare(configData.dto());

        // Assertions
        assertThat(result.isValid()).isTrue();
        assertThat(result.getTotalPrice()).isEqualTo(353.0f); // 343 base + 10 conditional

        assertThat(result.getSelectedOptions()).hasSize(5);
        UUID expectedOptionId = configData.optionIds().get("Full-suspension");
        UUID selectedOptionId = result.getSelectedOptions().stream()
                .filter(opt -> opt.getPartId().equals(configData.partIds().get("Frame Type")))
                .findFirst()
                .orElseThrow(() -> new AssertionError("Part not found in response"))
                .getOptionId();

        assertThat(selectedOptionId).isEqualTo(expectedOptionId);
    }

    private record ConfigTestData(ConfigurationDTO dto, Map<String, UUID> partIds, Map<String, UUID> optionIds) {}

    private ConfigTestData getConfigurationTestData(Product product, UUID productId) {
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

        ConfigurationDTO dto = ConfigurationDTO.builder()
                .productId(productId)
                .selectedOptions(selectedOptions)
                .quantity(1)
                .preset(false)
                .presetPrice(0f)
                .presetName(null)
                .build();

        return new ConfigTestData(dto, partIds, optionIds);
    }
}
