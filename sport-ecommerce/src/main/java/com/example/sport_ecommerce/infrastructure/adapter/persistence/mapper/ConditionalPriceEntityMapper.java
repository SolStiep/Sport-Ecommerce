package com.example.sport_ecommerce.infrastructure.adapter.persistence.mapper;

import com.example.sport_ecommerce.domain.model.ConditionalPrice;
import com.example.sport_ecommerce.domain.model.rule.Rule;
import com.example.sport_ecommerce.infrastructure.adapter.persistence.jpa.ConditionalPriceEntity;
import com.example.sport_ecommerce.infrastructure.adapter.persistence.jpa.RuleEntity;
import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;
import java.util.UUID;

@Mapper(componentModel = "spring")
public abstract class ConditionalPriceEntityMapper {

    @Autowired
    protected RuleEntityMapper ruleEntityMapper;

    public ConditionalPrice toDomain(ConditionalPriceEntity entity, Map<UUID, Rule> ruleMap) {
        RuleEntity ruleEntity = entity.getCondition();
        Rule rule = (ruleEntity != null && ruleEntity.getId() != null)
                ? ruleMap.get(ruleEntity.getId())
                : null;

        return new ConditionalPrice(entity.getId(), rule, entity.getPrice());
    }
}
