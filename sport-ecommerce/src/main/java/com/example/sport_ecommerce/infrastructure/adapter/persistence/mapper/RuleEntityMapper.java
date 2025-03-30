package com.example.sport_ecommerce.infrastructure.adapter.persistence.mapper;

import com.example.sport_ecommerce.domain.model.PartOption;
import com.example.sport_ecommerce.domain.model.rule.PriceConditionRule;
import com.example.sport_ecommerce.domain.model.rule.RestrictionRule;
import com.example.sport_ecommerce.domain.model.rule.Rule;
import com.example.sport_ecommerce.domain.model.valueobject.RuleOperator;
import com.example.sport_ecommerce.infrastructure.adapter.persistence.jpa.PartOptionEntity;
import com.example.sport_ecommerce.infrastructure.adapter.persistence.jpa.PriceConditionRuleEntity;
import com.example.sport_ecommerce.infrastructure.adapter.persistence.jpa.RestrictionRuleEntity;
import com.example.sport_ecommerce.infrastructure.adapter.persistence.jpa.RuleEntity;
import org.mapstruct.Mapper;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public abstract class RuleEntityMapper {

    public RuleEntity toEntity(Rule rule, List<PartOptionEntity> partOptionEntities) {
        if (rule instanceof PriceConditionRule) {
            return toPriceConditionRuleEntity((PriceConditionRule) rule, partOptionEntities);
        } else if (rule instanceof RestrictionRule) {
            return toRestrictionRuleEntity((RestrictionRule) rule, partOptionEntities);
        }
        throw new IllegalArgumentException("Unknown rule type");
    }

    private PriceConditionRuleEntity toPriceConditionRuleEntity(PriceConditionRule rule, List<PartOptionEntity> partOptionEntities) {
        PriceConditionRuleEntity entity = new PriceConditionRuleEntity();

        PartOptionEntity ifOptionEntity = findPartOptionEntity(rule.getIfOption(), partOptionEntities);
        entity.setIfOption(ifOptionEntity);

        List<PartOptionEntity> requiredOptionEntities = rule.getRequiredOptions().stream()
                .map(option -> findPartOptionEntity(option, partOptionEntities))
                .toList();
        entity.setRequiredOptions(requiredOptionEntities);

        return entity;
    }

    private RestrictionRuleEntity toRestrictionRuleEntity(RestrictionRule rule, List<PartOptionEntity> partOptionEntities) {
        RestrictionRuleEntity entity = new RestrictionRuleEntity();

        PartOptionEntity ifOptionEntity = findPartOptionEntity(rule.getIfOption(), partOptionEntities);
        entity.setIfOption(ifOptionEntity);

        List<PartOptionEntity> targetOptionEntities = rule.getTargetOptions().stream()
                .map(option -> findPartOptionEntity(option, partOptionEntities))
                .toList();
        entity.setTargetOptions(targetOptionEntities);

        entity.setOperator(rule.getOperator().name());

        return entity;
    }

    private PartOptionEntity findPartOptionEntity(PartOption option, List<PartOptionEntity> partOptionEntities) {
        return partOptionEntities.stream()
                .filter(entity -> entity.getId().equals(option.getId()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("PartOptionEntity not found for ID: " + option.getId()));
    }

    public Rule toDomain(RuleEntity ruleEntity, List<PartOptionEntity> partOptionEntities) {
        if (ruleEntity instanceof PriceConditionRuleEntity priceConditionEntity) {
            return toPriceConditionRule(priceConditionEntity, partOptionEntities);
        } else if (ruleEntity instanceof RestrictionRuleEntity restrictionEntity) {
            return toRestrictionRule(restrictionEntity, partOptionEntities);
        }
        throw new IllegalArgumentException("Unknown rule entity type: " + ruleEntity.getClass());
    }

    private PriceConditionRule toPriceConditionRule(PriceConditionRuleEntity entity, List<PartOptionEntity> partOptionEntities) {
        PartOption ifOption = findPartOption(entity.getIfOption(), partOptionEntities);
        List<PartOption> requiredOptions = entity.getRequiredOptions().stream()
                .map(partOptionEntity -> findPartOption(partOptionEntity, partOptionEntities))
                .collect(Collectors.toList());

        return new PriceConditionRule(ifOption, requiredOptions);
    }

    private RestrictionRule toRestrictionRule(RestrictionRuleEntity entity, List<PartOptionEntity> partOptionEntities) {
        PartOption ifOption = findPartOption(entity.getIfOption(), partOptionEntities);
        List<PartOption> targetOptions = entity.getTargetOptions().stream()
                .map(partOptionEntity -> findPartOption(partOptionEntity, partOptionEntities))
                .collect(Collectors.toList());

        return new RestrictionRule(ifOption, RuleOperator.valueOf(entity.getOperator()), targetOptions);
    }

    private PartOption findPartOption(PartOptionEntity entity, List<PartOptionEntity> partOptionEntities) {
        return partOptionEntities.stream()
                .filter(optionEntity -> optionEntity.getId().equals(entity.getId()))
                .findFirst()
                .map(partOptionEntity -> new PartOption(partOptionEntity.getId()))
                .orElseThrow(() -> new IllegalArgumentException("PartOptionEntity not found for ID: " + entity.getId()));
    }
}
