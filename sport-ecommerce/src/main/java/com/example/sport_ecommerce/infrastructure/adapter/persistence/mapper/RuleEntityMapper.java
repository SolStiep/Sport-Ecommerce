package com.example.sport_ecommerce.infrastructure.adapter.persistence.mapper;

import com.example.sport_ecommerce.domain.model.PartOption;
import com.example.sport_ecommerce.domain.model.rule.PriceConditionRule;
import com.example.sport_ecommerce.domain.model.rule.RestrictionRule;
import com.example.sport_ecommerce.infrastructure.adapter.persistence.jpa.PartOptionEntity;
import com.example.sport_ecommerce.infrastructure.adapter.persistence.jpa.PriceConditionRuleEntity;
import com.example.sport_ecommerce.infrastructure.adapter.persistence.jpa.RestrictionRuleEntity;
import org.mapstruct.Mapper;

import java.util.List;
import java.util.Map;

@Mapper(componentModel = "spring", uses = { PartOptionNameMapper.class })
public interface RuleEntityMapper {

    default RestrictionRuleEntity toEntity(RestrictionRule rule, Map<PartOption, PartOptionEntity> optionMap) {
        RestrictionRuleEntity entity = new RestrictionRuleEntity();
        entity.setIfOption(optionMap.get(rule.getIfOption()));
        entity.setTargetOptions(rule.getTargetOptions().stream()
                .map(optionMap::get)
                .toList());
        entity.setOperator(rule.getOperator().name());
        return entity;
    }

    default PriceConditionRuleEntity toEntity(PriceConditionRule rule, Map<PartOption, PartOptionEntity> optionMap) {
        PriceConditionRuleEntity entity = new PriceConditionRuleEntity();
        entity.setId(rule.getId());
        entity.setIfOption(optionMap.get(rule.getIfOption()));
        entity.setRequiredOptions(rule.getRequiredOptions().stream()
                .map(optionMap::get)
                .toList());
        return entity;
    }

    default PriceConditionRule toDomain(PriceConditionRuleEntity entity) {
        PartOption ifOpt = new PartOption(
                entity.getIfOption().getId(),
                entity.getIfOption().getName(),
                entity.getIfOption().getPrice(),
                entity.getIfOption().isInStock(),
                List.of()
        );

        List<PartOption> required = entity.getRequiredOptions().stream()
                .map(opt -> new PartOption(
                        opt.getId(), opt.getName(), opt.getPrice(), opt.isInStock(), List.of()))
                .toList();

        PriceConditionRule rule = new PriceConditionRule(ifOpt, required);
        rule.setId(entity.getId());
        return rule;
    }

    RestrictionRule toDomain(RestrictionRuleEntity entity);
}
