package com.example.sport_ecommerce.infrastructure.adapter.persistence.mapper;

import com.example.sport_ecommerce.domain.model.ConditionalPrice;
import com.example.sport_ecommerce.domain.model.PartOption;
import com.example.sport_ecommerce.domain.model.rule.Rule;
import com.example.sport_ecommerce.infrastructure.adapter.persistence.jpa.*;
import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Mapper(componentModel = "spring", uses = { ConditionalPriceEntityMapper.class })
public abstract class PartOptionEntityMapper {

    @Autowired
    protected ConditionalPriceEntityMapper conditionalPriceEntityMapper;

    public PartOptionEntity toEntity(PartOption option, PartEntity parentPart, Map<UUID, RuleEntity> ruleEntityMap) {
        return PartOptionEntity.builder()
                .name(option.getName())
                .price(option.getPrice())
                .inStock(option.isInStock())
                .part(parentPart)
                .build();
    }

    public void mapConditionalPrices(
            PartOption option,
            PartOptionEntity optionEntity,
            Map<Rule, RuleEntity> ruleEntityMap
    ) {
        List<ConditionalPriceEntity> condEntities = option.getConditionalPrices().stream()
                .map(cp -> {
                    RuleEntity conditionEntity = ruleEntityMap.get(cp.getCondition());
                    if (!(conditionEntity instanceof PriceConditionRuleEntity priceEntity)) {
                        throw new IllegalStateException("Expected PriceConditionRuleEntity but got: " + conditionEntity.getClass().getName());
                    }

                    return ConditionalPriceEntity.builder()
                            .price(cp.getPrice())
                            .condition(priceEntity)
                            .partOption(optionEntity)
                            .build();
                })
                .toList();

        optionEntity.setConditionalPrices(condEntities);
    }

    public PartOption toDomain(PartOptionEntity entity, Map<UUID, Rule> ruleMap) {
        return new PartOption(
                entity.getId(),
                entity.getName(),
                entity.getPrice(),
                entity.isInStock(),
                new ArrayList<>()
        );
    }

}
