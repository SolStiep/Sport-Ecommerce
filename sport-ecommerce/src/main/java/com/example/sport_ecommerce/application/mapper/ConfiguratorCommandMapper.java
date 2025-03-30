package com.example.sport_ecommerce.application.mapper;

import com.example.sport_ecommerce.application.model.dto.*;
import com.example.sport_ecommerce.domain.model.Part;
import com.example.sport_ecommerce.domain.model.PartOption;
import com.example.sport_ecommerce.domain.model.Product;
import com.example.sport_ecommerce.domain.model.rule.PriceConditionRule;
import com.example.sport_ecommerce.domain.model.rule.RestrictionRule;
import com.example.sport_ecommerce.domain.model.rule.Rule;
import com.example.sport_ecommerce.domain.model.service.Configurator;
import com.example.sport_ecommerce.domain.model.valueobject.RuleOperator;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static com.example.sport_ecommerce.application.utils.ProductStructureUtils.findOptionById;

@Component
public class ConfiguratorCommandMapper {
    public Configurator toDomain(ConfiguratorDTO command, Product product) {
        List<Part> parts = product.getParts();
        List<Rule> rules = mapRulesAndStorePriceRules(command.getRules(), parts);


        Configurator configurator = new Configurator(product, rules, command.getPriceStrategyType());
        product.setConfigurator(configurator);
        return configurator;
    }

    private List<Rule> mapRulesAndStorePriceRules(RuleDTO ruleDTOs, List<Part> parts) {
        List<Rule> rules = new ArrayList<>();

        for (RestrictionRuleDTO restrictionRuleDTO : ruleDTOs.getRestrictionRules()) {
            rules.add(mapRestrictionRule(restrictionRuleDTO, parts));
        }

        for (PriceConditionRuleDTO priceConditionRuleDTO : ruleDTOs.getPriceConditionRules()) {
            PriceConditionRule rule = mapPriceConditionRule(priceConditionRuleDTO, parts);
            rules.add(rule);
        }

        return rules;
    }

    private RestrictionRule mapRestrictionRule(RestrictionRuleDTO dto, List<Part> parts) {
        PartOption ifOption = findOptionById(parts, dto.getIfOption());
        List<PartOption> targets = dto.getTargetOptions().stream()
                .map(id -> findOptionById(parts, id))
                .toList();

        return new RestrictionRule(ifOption, RuleOperator.valueOf(dto.getOperator()), targets);
    }

    private PriceConditionRule mapPriceConditionRule(PriceConditionRuleDTO dto, List<Part> parts) {
        PartOption ifOption = findOptionById(parts, dto.getIfOption());
        List<PartOption> requiredOptions = dto.getRequiredOptions().stream()
                .map(id -> findOptionById(parts, id))
                .toList();
        PriceConditionRule rule = new PriceConditionRule(ifOption, requiredOptions, dto.getPrice());
        ifOption.addPriceConditionRule(rule);

        return rule;
    }
}
