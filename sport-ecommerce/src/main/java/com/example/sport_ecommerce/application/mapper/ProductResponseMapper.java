package com.example.sport_ecommerce.application.mapper;

import com.example.sport_ecommerce.application.model.dto.RuleDTO;
import com.example.sport_ecommerce.application.model.response.*;
import com.example.sport_ecommerce.application.service.product.RuleDTOVisitor;
import com.example.sport_ecommerce.domain.model.Product;
import com.example.sport_ecommerce.domain.model.rule.Rule;
import com.example.sport_ecommerce.domain.model.service.Configurator;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = ProductSummaryMapper.class)
public interface ProductResponseMapper {

    @Mapping(source = "id", target = "id")
    @Mapping(source = "category.id", target = "categoryId")
    @Mapping(source = "category.name", target = "categoryName")
    @Mapping(target = "configurator", expression = "java(mapConfiguratorToResponse(product))")
    ProductResponse toResponse(Product product);

    default ConfiguratorResponse mapConfiguratorToResponse(Product product) {
        Configurator configurator = product.getConfigurator();
        if (configurator != null) {
            return ConfiguratorResponse.builder()
                    .id(configurator.getId())
                    .priceStrategyType(configurator.getPriceStrategyType())
                    .rules(mapRulesToResponse(configurator.getRules()))
                    .build();
        }
        return null;
    }

    default RuleDTO mapRulesToResponse(List<Rule> rules) {
        RuleDTOVisitor ruleVisitor = new RuleDTOVisitor();
        rules.forEach(rule -> rule.accept(ruleVisitor));
        return ruleVisitor.getRuleDTOs();
    }
}
