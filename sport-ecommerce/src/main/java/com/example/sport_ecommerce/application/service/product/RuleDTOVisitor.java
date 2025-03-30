package com.example.sport_ecommerce.application.service.product;

import com.example.sport_ecommerce.application.model.dto.PriceConditionRuleDTO;
import com.example.sport_ecommerce.application.model.dto.RestrictionRuleDTO;
import com.example.sport_ecommerce.application.model.dto.RuleDTO;
import com.example.sport_ecommerce.domain.model.PartOption;
import com.example.sport_ecommerce.domain.model.rule.PriceConditionRule;
import com.example.sport_ecommerce.domain.model.rule.RestrictionRule;
import com.example.sport_ecommerce.domain.model.rule.RuleVisitor;
import lombok.Getter;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Getter
@Component
public class RuleDTOVisitor implements RuleVisitor {

    private final RuleDTO ruleDTOs = new RuleDTO();

    @Override
    public void visit(RestrictionRule restrictionRule) {
        RestrictionRuleDTO dto = RestrictionRuleDTO.builder()
                .id(restrictionRule.getId())
                .ifOption(restrictionRule.getIfOption().getId())
                .operator(restrictionRule.getOperator().name())
                .targetOptions(restrictionRule.getTargetOptions().stream()
                        .map(PartOption::getId)
                        .collect(Collectors.toList()))
                .build();
        ruleDTOs.addToRestrictionRules(dto);
    }

    @Override
    public void visit(PriceConditionRule priceConditionRule) {
        PriceConditionRuleDTO dto = PriceConditionRuleDTO.builder()
                .id(priceConditionRule.getId())
                .ifOption(priceConditionRule.getIfOption().getId())
                .requiredOptions(priceConditionRule.getRequiredOptions().stream()
                        .map(PartOption::getId)
                        .collect(Collectors.toList()))
                .price(priceConditionRule.getPrice())
                .build();
        ruleDTOs.addToPriceConditionRules(dto);
    }
}

