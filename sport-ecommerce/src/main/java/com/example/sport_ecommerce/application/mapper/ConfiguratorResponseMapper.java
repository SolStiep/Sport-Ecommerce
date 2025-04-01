package com.example.sport_ecommerce.application.mapper;

import com.example.sport_ecommerce.application.model.dto.RuleDTO;
import com.example.sport_ecommerce.application.model.response.ConfiguratorResponse;
import com.example.sport_ecommerce.application.service.product.RuleDTOVisitor;
import com.example.sport_ecommerce.domain.model.rule.Rule;
import com.example.sport_ecommerce.domain.model.service.Configurator;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ConfiguratorResponseMapper {
    default ConfiguratorResponse toResponse(Configurator configurator) {
            return ConfiguratorResponse.builder()
                    .id(configurator.getId())
                    .priceStrategyType(configurator.getPriceStrategyType())
                    .rules(mapRulesToResponse(configurator.getRules()))
                    .build();
    }

    default RuleDTO mapRulesToResponse(List<Rule> rules) {
        RuleDTOVisitor ruleVisitor = new RuleDTOVisitor();
        rules.forEach(rule -> rule.accept(ruleVisitor));
        return ruleVisitor.getRuleDTOs();
    }
}
