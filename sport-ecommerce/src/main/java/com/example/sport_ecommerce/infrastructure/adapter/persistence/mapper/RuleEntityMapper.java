package com.example.sport_ecommerce.infrastructure.adapter.persistence.mapper;

import com.example.sport_ecommerce.domain.model.Part;
import com.example.sport_ecommerce.domain.model.PartOption;
import com.example.sport_ecommerce.domain.model.rule.PriceConditionRule;
import com.example.sport_ecommerce.domain.model.rule.RestrictionRule;
import com.example.sport_ecommerce.infrastructure.adapter.persistence.jpa.PriceConditionRuleEntity;
import com.example.sport_ecommerce.infrastructure.adapter.persistence.jpa.RestrictionRuleEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", uses = { PartOptionNameMapper.class })
public interface RuleEntityMapper {

    @Mapping(target = "ifOption", source = "ifOption", qualifiedByName = "toName")
    @Mapping(target = "targetOptions", source = "targetOptions", qualifiedByName = "toNames")
    RestrictionRuleEntity toEntity(RestrictionRule rule);

    @Mapping(target = "requiredOptions", source = "requiredOptions", qualifiedByName = "mapRequiredOptions")
    PriceConditionRuleEntity toEntity(PriceConditionRule rule);

    @Mapping(target = "ifOption", source = "ifOption", qualifiedByName = "fromName")
    @Mapping(target = "targetOptions", source = "targetOptions", qualifiedByName = "fromNames")
    RestrictionRule toDomain(RestrictionRuleEntity entity);

    @Mapping(target = "requiredOptions", source = "requiredOptions", qualifiedByName = "mapRequiredOptionsBack")
    PriceConditionRule toDomain(PriceConditionRuleEntity entity);

    @Named("mapRequiredOptions")
    default Map<String, String> mapRequiredOptions(Map<Part, PartOption> map) {
        if (map == null) return Map.of();
        return map.entrySet().stream()
                .collect(Collectors.toMap(
                        e -> e.getKey().getName(),
                        e -> e.getValue().getName()
                ));
    }

    @Named("mapRequiredOptionsBack")
    default Map<Part, PartOption> mapRequiredOptionsBack(Map<String, String> map) {
        if (map == null) return Map.of();
        return map.entrySet().stream()
                .collect(Collectors.toMap(
                        e -> new Part(null, e.getKey(), null, null),
                        e -> new PartOption(null, e.getValue(), 0f, true, List.of())
                ));
    }
}
