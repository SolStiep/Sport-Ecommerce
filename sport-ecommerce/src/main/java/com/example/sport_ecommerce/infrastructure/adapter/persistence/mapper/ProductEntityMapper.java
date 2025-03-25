package com.example.sport_ecommerce.infrastructure.adapter.persistence.mapper;

import com.example.sport_ecommerce.domain.model.*;
import com.example.sport_ecommerce.domain.model.rule.Rule;
import com.example.sport_ecommerce.domain.model.service.Configurator;
import com.example.sport_ecommerce.infrastructure.adapter.persistence.jpa.*;
import lombok.Getter;
import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

@Mapper(componentModel = "spring")
public abstract class ProductEntityMapper {

    @Autowired protected CategoryEntityMapper categoryEntityMapper;
    @Getter @Autowired protected RuleEntityMapper ruleEntityMapper;
    @Autowired protected PartOptionEntityMapper partOptionEntityMapper;
    @Autowired protected ConfiguratorEntityMapper configuratorMapper;

    public ProductEntity toEntity(Product product) {
        if (product == null) return null;

        ProductEntity entity = ProductEntity.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .build();

        if (product.getCategory() != null) {
            entity.setCategory(categoryEntityMapper.toEntity(product.getCategory()));
        }

        Map<Rule, RuleEntity> ruleEntityMap = new IdentityHashMap<>();

        if (product.getConfigurator() != null) {
            ConfiguratorEntity configuratorEntity = configuratorMapper.toEntity(product.getConfigurator(), entity, ruleEntityMap);
            configuratorEntity.setProduct(entity);
            entity.setConfigurator(configuratorEntity);
        }

        if (product.getParts() != null) {
            List<PartEntity> partEntities = product.getParts().stream().map(part -> {
                PartEntity partEntity = PartEntity.builder()
                        .id(part.getId())
                        .name(part.getName())
                        .product(entity)
                        .build();

                if (part.getOptions() != null) {
                    List<PartOptionEntity> optionEntities = part.getOptions().stream()
                            .map(option -> partOptionEntityMapper.toEntity(option, partEntity, ruleEntityMap))
                            .toList();
                    partEntity.setOptions(optionEntities);
                }

                return partEntity;
            }).toList();

            entity.setParts(partEntities);
        }

        return entity;
    }

    public Product toDomain(ProductEntity entity) {
            if (entity == null) return null;

            Product product = new Product(
                    entity.getId(),
                    entity.getName(),
                    entity.getDescription(),
                    null,
                    new ArrayList<>(),
                    null
            );

            if (entity.getCategory() != null) {
                product.setCategory(new Category(
                        entity.getCategory().getId(),
                        entity.getCategory().getName(),
                        entity.getCategory().getDescription()
                ));
            }

            Map<UUID, Rule> ruleMap = new HashMap<>();
            Configurator configurator = null;

            if (entity.getConfigurator() != null) {
                List<Rule> rules = entity.getConfigurator().getRules().stream()
                        .map(ruleEntity -> {
                            Rule rule;
                            if (ruleEntity instanceof RestrictionRuleEntity restriction) {
                                rule = configuratorMapper.getRuleEntityMapper().toDomain(restriction);
                            } else if (ruleEntity instanceof PriceConditionRuleEntity priceCondition) {
                                rule = configuratorMapper.getRuleEntityMapper().toDomain(priceCondition);
                            } else {
                                throw new IllegalArgumentException("Unknown rule type: " + ruleEntity.getClass());
                            }

                            if (ruleEntity.getId() != null) {
                                rule.setId(ruleEntity.getId());
                                ruleMap.put(ruleEntity.getId(), rule);
                            }

                            return rule;
                        }).toList();

                configurator = new Configurator(
                        entity.getConfigurator().getId(),
                        product,
                        rules,
                        entity.getConfigurator().getPriceStrategyType()
                );
                configurator.setProduct(product);
                product.setConfigurator(configurator);
            }

            if (entity.getParts() != null) {
                List<Part> parts = new ArrayList<>();
                Map<PartEntity, Part> partEntityMap = new HashMap<>();

                for (PartEntity partEntity : entity.getParts()) {
                    Part part = new Part(
                            partEntity.getId(),
                            partEntity.getName(),
                            product,
                            new ArrayList<>()
                    );
                    parts.add(part);
                    partEntityMap.put(partEntity, part);
                }

                product.setParts(parts);

                for (PartEntity partEntity : entity.getParts()) {
                    Part part = partEntityMap.get(partEntity);
                    List<PartOption> options = new ArrayList<>();

                    for (PartOptionEntity optEntity : partEntity.getOptions()) {
                        PartOption option = partOptionEntityMapper.toDomain(optEntity, ruleMap);
                        options.add(option);
                    }

                    part.setOptions(options);
                }

            }

            return product;
        }
}
