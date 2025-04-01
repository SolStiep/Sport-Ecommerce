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

    public Configurator toDomain(ConfiguratorEntity entity, List<PartOptionEntity> partOptionEntities) {
        List<Rule> rules = entity.getRules().stream()
                .map(ruleEntity -> {
                    if (ruleEntity instanceof RestrictionRuleEntity restriction) {
                        return ruleEntityMapper.toDomain(restriction, partOptionEntities);
                    } else if (ruleEntity instanceof PriceConditionRuleEntity priceCondition) {
                        return ruleEntityMapper.toDomain(priceCondition, partOptionEntities);
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

