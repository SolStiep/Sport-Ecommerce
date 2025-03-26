package com.example.sport_ecommerce.application.mapper;

import com.example.sport_ecommerce.application.model.dto.RuleDTO;
import com.example.sport_ecommerce.application.model.response.PrepareConfigurationResponse;
import com.example.sport_ecommerce.application.model.response.SelectedOptionResponse;
import com.example.sport_ecommerce.application.service.product.RuleDTOVisitor;
import com.example.sport_ecommerce.domain.model.Configuration;
import com.example.sport_ecommerce.domain.model.rule.Rule;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

@Component
public class PrepareConfigurationResponseMapper {
    public PrepareConfigurationResponse toResponse(Configuration config, float price, boolean valid) {
        List<SelectedOptionResponse> selected = config.getSelectedOptions().entrySet().stream()
                .map(entry -> SelectedOptionResponse.builder()
                        .partId(entry.getKey().getId())
                        .optionId(entry.getValue().getId())
                        .price(entry.getValue().getPrice())
                        .build())
                .toList();

        RuleDTO rulesDto = mapRulesToResponse(config.getViolationRules());

        return PrepareConfigurationResponse.builder()
                .productId(config.getProduct().getId())
                .productName(config.getProduct().getName())
                .totalPrice(price)
                .valid(valid)
                .selectedOptions(selected)
                .restrictionViolations(rulesDto.getRestrictionRules())
                .priceViolations(rulesDto.getPriceConditionRules())
                .build();
    }

    private RuleDTO mapRulesToResponse(Set<Rule> rules) {
        RuleDTOVisitor ruleVisitor = new RuleDTOVisitor();
        rules.forEach(rule -> rule.accept(ruleVisitor));
        return ruleVisitor.getRuleDTOs();
    }
}
