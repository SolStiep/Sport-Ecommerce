package com.example.sport_ecommerce.integration;

import com.example.sport_ecommerce.application.port.out.ConfiguratorRepositoryPort;
import com.example.sport_ecommerce.application.port.out.ProductRepositoryPort;
import com.example.sport_ecommerce.domain.model.*;
import com.example.sport_ecommerce.domain.model.rule.PriceConditionRule;
import com.example.sport_ecommerce.domain.model.rule.RestrictionRule;
import com.example.sport_ecommerce.domain.model.rule.Rule;
import com.example.sport_ecommerce.domain.model.service.Configurator;
import com.example.sport_ecommerce.domain.model.valueobject.PriceStrategyType;
import com.example.sport_ecommerce.domain.model.valueobject.RuleOperator;
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

import static com.example.sport_ecommerce.application.utils.ProductStructureUtils.findOptionByName;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
public class ProductPersistenceIntegrationTest {
    @Autowired private ProductJpaRepository productJpaRepository;
    @Autowired private CategoryJpaRepository categoryJpaRepository;
    @Autowired private ProductEntityMapper productMapper;
    @Autowired private CategoryEntityMapper categoryMapper;
    @Autowired private ProductRepositoryPort productRepositoryPort;
    @Autowired private ConfiguratorRepositoryPort configuratorRepository;

    @Test
    void should_persist_and_restore_product_with_rules() {
        // Arrange: persist product with configurator and rules
        var result = TestDataHelper.persistSampleProductWithRules(
                productRepositoryPort,
                categoryJpaRepository,
                productMapper,
                categoryMapper,
                configuratorRepository
        );

        // Act: re-fetch the product from DB
        Product restored = productRepositoryPort.findById(result.productId()).orElseThrow();

        // Assert: product data
        assertThat(restored).isNotNull();
        assertThat(restored.getParts()).hasSize(5);

        // Assert: configurator is restored
        Configurator configurator = restored.getConfigurator();
        assertThat(configurator).isNotNull();
        assertThat(configurator.getProduct()).isNotNull(); // bidirectional
        assertThat(configurator.getProduct().getId()).isEqualTo(restored.getId());

        // Assert: rules
        List<Rule> rules = configurator.getRules();
        assertThat(rules).hasSize(2);

        // Assert: price rule
        PriceConditionRule priceRule = rules.stream()
                .filter(r -> r instanceof PriceConditionRule)
                .map(r -> (PriceConditionRule) r)
                .findFirst()
                .orElseThrow();

        assertThat(priceRule.getIfOption().getName()).isEqualTo("Full-suspension");
        assertThat(priceRule.getRequiredOptions())
                .extracting(PartOption::getName)
                .containsExactly("Blue");
        assertThat(priceRule.getPrice()).isEqualTo(10f);
    }

    @Test
    void should_create_configurator_and_restore_rules_properly() {
        var result = TestDataHelper.persistSampleProductWithoutRules(
                productJpaRepository, categoryJpaRepository, productMapper, categoryMapper
        );

        Product product = result.domain();

        // Persist and get the configurator
        Configurator configurator = TestDataHelper.createAndPersistConfigurator(product, configuratorRepository, productRepositoryPort);

        assertThat(configurator).isNotNull();
        assertThat(configurator.getRules()).hasSize(2);

        // Ensure the configurator knows its product
        Product linkedProduct = configurator.getProduct();
        assertThat(linkedProduct).isNotNull();
        assertThat(linkedProduct.getId()).isEqualTo(product.getId());

        List<PriceConditionRule> priceRules = configurator.getRules().stream()
                .filter(r -> r instanceof PriceConditionRule)
                .map(r -> (PriceConditionRule) r)
                .toList();

        assertThat(priceRules).hasSize(1);
        assertThat(priceRules.get(0).getIfOption().getName()).isEqualTo("Full-suspension");
    }

}

