package com.example.sport_ecommerce.infrastructure.adapter.persistence.mapper;

import com.example.sport_ecommerce.domain.model.ConditionalPrice;
import com.example.sport_ecommerce.domain.model.rule.PriceConditionRule;
import com.example.sport_ecommerce.infrastructure.adapter.persistence.jpa.ConditionalPriceEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class ConditionalPriceMapper {

    private final RuleEntityMapper ruleEntityMapper;

    public ConditionalPriceEntity toEntity(ConditionalPrice domain) {
        if (!(domain.getCondition() instanceof PriceConditionRule rule)) {
            throw new UnsupportedOperationException("Only PriceConditionRule supported");
        }

        return ConditionalPriceEntity.builder()
                .id(UUID.randomUUID())
                .price(domain.getPrice())
                .condition(ruleEntityMapper.toEntity(rule))
                .build();
    }

    public ConditionalPrice toDomain(ConditionalPriceEntity entity) {
        PriceConditionRule condition = ruleEntityMapper.toDomain(entity.getCondition());
        return new ConditionalPrice(condition, entity.getPrice());
    }
}
