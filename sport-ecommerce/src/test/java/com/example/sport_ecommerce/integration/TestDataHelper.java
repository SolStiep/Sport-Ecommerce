package com.example.sport_ecommerce.integration;

import com.example.sport_ecommerce.application.port.out.ConfiguratorRepositoryPort;
import com.example.sport_ecommerce.application.port.out.ProductRepositoryPort;
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

import static com.example.sport_ecommerce.application.utils.ProductStructureUtils.findOptionByName;

public class TestDataHelper {

    public record PersistedProduct(UUID productId, Product domain) {}

    public static PersistedProduct persistSampleProductWithoutRules(
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

        // Persist Category
        CategoryEntity catEntity = categoryMapper.toEntity(category);
        categoryJpaRepository.save(catEntity);
        Category persistedCategory = categoryMapper.toDomain(catEntity);
        // Assign to product
        Product product = new Product("Bike", "Description", persistedCategory,
                List.of(frameType, rimColor, frameFinish, wheels, chainPart), null);

        // Persist Product
        ProductEntity entity = productMapper.toEntity(product);
        ProductEntity savedEntity = productJpaRepository.save(entity);

        Product restored = productMapper.toDomain(savedEntity);

        return new PersistedProduct(savedEntity.getId(), restored);
    }


    public static PersistedProduct persistSampleProductWithRules(
            ProductRepositoryPort productRepo,
            CategoryJpaRepository categoryJpaRepository,
            ProductEntityMapper productMapper,
            CategoryEntityMapper categoryMapper,
            ConfiguratorRepositoryPort configuratorRepo
    ) {
        PersistedProduct base = persistSampleProductWithoutRules(
                productRepo.getJpaRepository(),
                categoryJpaRepository,
                productMapper,
                categoryMapper
        );

        Product product = base.domain();

        createAndPersistConfigurator(product, configuratorRepo, productRepo);

        return new PersistedProduct(product.getId(), productRepo.findById(product.getId()).orElseThrow());
    }

    public static Configurator createAndPersistConfigurator(Product product, ConfiguratorRepositoryPort configuratorRepo, ProductRepositoryPort productRepo) {
        PartOption fullSuspension = findOptionByName(product.getParts(), "Full-suspension");
        PartOption blue = findOptionByName(product.getParts(), "Blue");
        PartOption mountainWheels = findOptionByName(product.getParts(), "Mountain wheels");

        RestrictionRule restriction = new RestrictionRule(mountainWheels, RuleOperator.REQUIRES, List.of(fullSuspension));
        PriceConditionRule priceRule = new PriceConditionRule(fullSuspension, List.of(blue), 10f);

        Configurator configurator = new Configurator(product, List.of(restriction, priceRule), PriceStrategyType.SIMPLE);
        Configurator savedConfigurator = configuratorRepo.save(configurator);
        product.setConfigurator(savedConfigurator);
        productRepo.save(product);

        return savedConfigurator;
    }
}
