package com.example.sport_ecommerce.infrastructure.adapter.persistence.mapper;

import com.example.sport_ecommerce.domain.model.PartOption;
import com.example.sport_ecommerce.domain.model.rule.PriceConditionRule;
import com.example.sport_ecommerce.domain.model.rule.RestrictionRule;
import com.example.sport_ecommerce.domain.model.rule.Rule;
import com.example.sport_ecommerce.domain.model.service.Configurator;
import com.example.sport_ecommerce.infrastructure.adapter.persistence.jpa.*;
import lombok.Getter;
import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

@Getter
@Mapper(componentModel = "spring", uses = RuleEntityMapper.class)
public abstract class ConfiguratorEntityMapper {

    @Autowired
    protected RuleEntityMapper ruleEntityMapper;

    public ConfiguratorEntity toEntity(
            Configurator domain,
            ProductEntity product,
            Map<Rule, RuleEntity> ruleEntityMap,
            Map<PartOption, PartOptionEntity> optionMap
    ) {
        ConfiguratorEntity configuratorEntity = ConfiguratorEntity.builder()
                .product(product)
                .priceStrategyType(domain.getPriceStrategyType())
                .build();

        List<RuleEntity> ruleEntities = domain.getRules().stream()
                .map(rule -> {
                    RuleEntity entity = ruleEntityMap.computeIfAbsent(rule, id -> {
                        if (rule instanceof RestrictionRule restrictionRule) {
                            return ruleEntityMapper.toEntity(restrictionRule, optionMap);
                        } else if (rule instanceof PriceConditionRule priceConditionRule) {
                            return ruleEntityMapper.toEntity(priceConditionRule, optionMap);
                        }
                        throw new IllegalArgumentException("Unsupported rule type: " + rule.getClass());
                    });
                    entity.setConfigurator(configuratorEntity);
                    return entity;
                })
                .toList();

        configuratorEntity.setRules(ruleEntities);
        return configuratorEntity;
    }


    public Configurator toDomain(ConfiguratorEntity entity) {
        List<Rule> rules = entity.getRules().stream()
                .map(ruleEntity -> {
                    if (ruleEntity instanceof RestrictionRuleEntity restriction) {
                        return ruleEntityMapper.toDomain(restriction);
                    } else if (ruleEntity instanceof PriceConditionRuleEntity priceCondition) {
                        return ruleEntityMapper.toDomain(priceCondition);
                    }
                    throw new IllegalArgumentException("Unknown rule type: " + ruleEntity.getClass());
                }).toList();

        return new Configurator(
                null,
                rules,
                entity.getPriceStrategyType()
        );
    }

}

