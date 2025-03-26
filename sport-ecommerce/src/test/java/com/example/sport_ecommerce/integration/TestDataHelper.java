package com.example.sport_ecommerce.integration;

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

import java.util.*;

public class TestDataHelper {

    public record PersistedProduct(UUID productId, Product domain) {}

    public static PersistedProduct persistSampleProductWithRules(
            ProductJpaRepository productJpaRepository,
            CategoryJpaRepository categoryJpaRepository,
            ProductEntityMapper productMapper,
            CategoryEntityMapper categoryMapper
    ) {
        // Setup category
        Category category = new Category("Bicycles", "Custom bicycles");

        // Setup part options
        PartOption fullSuspension = new PartOption("Full-suspension", 130f, true, new ArrayList<>());
        PartOption blue = new PartOption("Blue", 20f, true, new ArrayList<>());
        PartOption matte = new PartOption("Matte", 50f, true, new ArrayList<>());
        PartOption mountainWheels = new PartOption("Mountain wheels", 100f, true, new ArrayList<>());
        PartOption chain = new PartOption("Single-speed chain", 43f, true, new ArrayList<>());


        // Setup parts
        Part frameType = new Part("Frame Type", null, List.of(fullSuspension));
        Part rimColor = new Part("Rim Color", null, List.of(blue));
        Part frameFinish = new Part("Frame Finish", null, List.of(matte));
        Part wheels = new Part("Wheels", null, List.of(mountainWheels));
        Part chainPart = new Part("Chain", null, List.of(chain));

        // Assign to product
        Product product = new Product("Bike", "Description", category,
                List.of(frameType, rimColor, frameFinish, wheels, chainPart), null);

        // Setup rules
        RestrictionRule restriction = new RestrictionRule(
                mountainWheels, RuleOperator.REQUIRES, List.of(fullSuspension));

        Map<Part, PartOption> required = Map.of(
                frameType, fullSuspension,
                rimColor, blue
        );
        PriceConditionRule priceRule = new PriceConditionRule(fullSuspension, List.of(blue));
        ConditionalPrice conditionalPrice = new ConditionalPrice(priceRule, 10f);
        fullSuspension.setConditionalPrices(List.of(conditionalPrice));

        Configurator configurator = new Configurator(product, List.of(restriction, priceRule), PriceStrategyType.SIMPLE);
        configurator.setProduct(product);
        product.setConfigurator(configurator);

        // Persist
        CategoryEntity catEntity = categoryMapper.toEntity(category);
        categoryJpaRepository.save(catEntity);
        ProductEntity entity = productMapper.toEntity(product);
        ProductEntity savedEntity = productJpaRepository.save(entity);
        Product restored = productMapper.toDomain(savedEntity);


        return new PersistedProduct(savedEntity.getId(), restored);
    }
}
