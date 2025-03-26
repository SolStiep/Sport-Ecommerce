package com.example.sport_ecommerce.integration;

import com.example.sport_ecommerce.domain.model.*;
import com.example.sport_ecommerce.domain.model.rule.PriceConditionRule;
import com.example.sport_ecommerce.domain.model.rule.Rule;
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
public class ProductPersistenceIntegrationTest {

    @Autowired
    private ProductJpaRepository productJpaRepository;

    @Autowired
    private CategoryJpaRepository categoryJpaRepository;

    @Autowired
    private ProductEntityMapper productMapper;

    @Autowired
    private CategoryEntityMapper categoryMapper;

    @Test
    void should_persist_and_restore_product_with_rules() {
        var result = TestDataHelper.persistSampleProductWithRules(
                productJpaRepository, categoryJpaRepository, productMapper, categoryMapper
        );

        ProductEntity loadedEntity = productJpaRepository.findById(result.productId()).orElseThrow();
        Product restored = productMapper.toDomain(loadedEntity);

        assertThat(restored.getParts()).hasSize(5);

        PartOption restoredFullSuspension = restored.getParts().stream()
                .flatMap(p -> p.getOptions().stream())
                .filter(o -> o.getName().equals("Full-suspension"))
                .findFirst()
                .orElseThrow();

        assertThat(restoredFullSuspension.getConditionalPrices()).hasSize(1);

        ConditionalPrice restoredPrice = restoredFullSuspension.getConditionalPrices().get(0);
        Rule condition = restoredPrice.getCondition();

        assertThat(condition).isInstanceOf(PriceConditionRule.class);
        PriceConditionRule restoredRule = (PriceConditionRule) condition;

        assertThat(restoredRule.getRequiredOptions()).extracting(PartOption::getName).contains("Blue");

        assertThat(restoredPrice.getPrice()).isEqualTo(10f);

        UUID expectedRuleId = restored.getConfigurator().getRules().stream()
                .filter(r -> r instanceof PriceConditionRule)
                .map(Rule::getId)
                .findFirst()
                .orElseThrow();

        assertThat(restoredPrice.getCondition().getId()).isEqualTo(expectedRuleId);
    }

}

