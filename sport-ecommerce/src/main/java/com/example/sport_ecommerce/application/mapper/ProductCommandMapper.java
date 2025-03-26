package com.example.sport_ecommerce.application.mapper;

import com.example.sport_ecommerce.application.model.command.ProductCommand;
import com.example.sport_ecommerce.application.model.dto.*;
import com.example.sport_ecommerce.domain.model.*;
import com.example.sport_ecommerce.domain.model.rule.PriceConditionRule;
import com.example.sport_ecommerce.domain.model.rule.RestrictionRule;
import com.example.sport_ecommerce.domain.model.rule.Rule;
import com.example.sport_ecommerce.domain.model.service.Configurator;
import com.example.sport_ecommerce.domain.model.valueobject.RuleOperator;
import org.springframework.stereotype.Component;

import java.util.*;

import static com.example.sport_ecommerce.application.utils.ProductStructureUtils.*;

@Component
public class ProductCommandMapper {
    public Product toDomain(ProductCommand command, Category category) {
        List<Part> parts = command.getParts().stream()
                .map(this::mapPart)
                .toList();

        Product product = new Product(
                command.getName(),
                command.getDescription(),
                category,
                parts,
                null
        );

        parts.forEach(p -> p.setProduct(product));

        Map<CreatePriceConditionRuleDTO, PriceConditionRule> ruleMap = new HashMap<>();

        List<Rule> rules = mapRulesAndStorePriceRules(command.getConfigurator().getRules(), parts, ruleMap);

        Configurator configurator = new Configurator(product, rules, command.getConfigurator().getPriceStrategyType());
        configurator.setProduct(product);
        product.setConfigurator(configurator);

        for (int i = 0; i < parts.size(); i++) {
            Part part = parts.get(i);
            PartDTO partDTO = command.getParts().get(i);

            for (int j = 0; j < part.getOptions().size(); j++) {
                PartOption option = part.getOptions().get(j);
                PartOptionDTO optionDTO = partDTO.getOptions().get(j);

                if (optionDTO.getConditionalPrices() != null) {
                    List<ConditionalPrice> condPrices = optionDTO.getConditionalPrices().stream()
                            .map(cpDTO -> {
                                PriceConditionRule rule = ruleMap.get(cpDTO.getRule());
                                if (rule == null) {
                                    throw new RuntimeException("No corresponding rule was found for: " + cpDTO.getRule());
                                }
                                return new ConditionalPrice(rule, cpDTO.getPrice());
                            })
                            .toList();
                    option.setConditionalPrices(condPrices);
                }
            }
        }

        return product;
    }

    private Part mapPart(PartDTO partDTO) {
        List<PartOption> options = partDTO.getOptions().stream()
                .map(this::mapPartOption)
                .toList();

        return new Part(partDTO.getName(), null, options);
    }

    private PartOption mapPartOption(PartOptionDTO dto) {
        return new PartOption(dto.getName(), dto.getPrice(), dto.isInStock(), List.of());
    }

    private List<Rule> mapRulesAndStorePriceRules(CreateRuleDTO ruleDTOs, List<Part> parts, Map<CreatePriceConditionRuleDTO, PriceConditionRule> priceRuleMap) {
        List<Rule> rules = new ArrayList<>();

        for (CreateRestrictionRuleDTO restrictionRuleDTO : ruleDTOs.getRestrictionRules()) {
            rules.add(mapRestrictionRule(restrictionRuleDTO, parts));
        }

        for (CreatePriceConditionRuleDTO priceConditionRuleDTO : ruleDTOs.getPriceConditionRules()) {
            PriceConditionRule rule = mapPriceConditionRule(priceConditionRuleDTO, parts);
            priceRuleMap.put(priceConditionRuleDTO, rule);
            rules.add(rule);
        }

        return rules;
    }

    private RestrictionRule mapRestrictionRule(CreateRestrictionRuleDTO dto, List<Part> parts) {
        PartOption ifOption = findOptionByName(parts, dto.getIfOption());
        List<PartOption> targets = dto.getTargetOptions().stream()
                .map(name -> findOptionByName(parts, name))
                .toList();

        return new RestrictionRule(ifOption, RuleOperator.valueOf(dto.getOperator()), targets);
    }

    private PriceConditionRule mapPriceConditionRule(CreatePriceConditionRuleDTO dto, List<Part> parts) {
        PartOption ifOption = findOptionByName(parts, dto.getIfOption());
        List<PartOption> requiredOptions = dto.getRequiredOptions().stream()
                .map(name -> findOptionByName(parts, name))
                .toList();

        return new PriceConditionRule(ifOption, requiredOptions);
    }

}
