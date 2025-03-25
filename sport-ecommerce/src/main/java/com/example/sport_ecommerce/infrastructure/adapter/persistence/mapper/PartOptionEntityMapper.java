package com.example.sport_ecommerce.infrastructure.adapter.persistence.mapper;

import com.example.sport_ecommerce.domain.model.ConditionalPrice;
import com.example.sport_ecommerce.domain.model.PartOption;
import com.example.sport_ecommerce.domain.model.rule.Rule;
import com.example.sport_ecommerce.infrastructure.adapter.persistence.jpa.*;
import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Mapper(componentModel = "spring", uses = { ConditionalPriceEntityMapper.class })
public abstract class PartOptionEntityMapper {

    @Autowired
    protected ConditionalPriceEntityMapper conditionalPriceEntityMapper;

    public PartOptionEntity toEntity(PartOption option, PartEntity parentPart, Map<Rule, RuleEntity> ruleEntityMap) {
        List<ConditionalPriceEntity> condEntities = option.getConditionalPrices().stream()
                .map(cp -> {
                    RuleEntity conditionEntity = ruleEntityMap.get(cp.getCondition());
                    if (conditionEntity == null) {
                        throw new RuntimeException("Rule not found in map for ConditionalPrice: " + cp);
                    }
                    return ConditionalPriceEntity.builder()
                            .price(cp.getPrice())
                            .condition((PriceConditionRuleEntity) conditionEntity)
                            .build();
                }).toList();

        PartOptionEntity entity = PartOptionEntity.builder()
                .name(option.getName())
                .price(option.getPrice())
                .inStock(option.isInStock())
                .conditionalPrices(condEntities)
                .part(parentPart)
                .build();

        condEntities.forEach(cp -> cp.setPartOption(entity));
        return entity;
    }

    public PartOption toDomain(PartOptionEntity entity, Map<UUID, Rule> ruleMap) {
        List<ConditionalPrice> conditionalPrices = entity.getConditionalPrices() != null ?
                entity.getConditionalPrices().stream()
                        .map(cp -> conditionalPriceEntityMapper.toDomain(cp, ruleMap))
                        .toList() : List.of();

        return new PartOption(
                entity.getId(),
                entity.getName(),
                entity.getPrice(),
                entity.isInStock(),
                conditionalPrices
        );
    }

}
