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

import static com.example.sport_ecommerce.application.utils.ProductStructureUtils.findOptionByName;
import static com.example.sport_ecommerce.application.utils.ProductStructureUtils.findPartByName;

@Component
public class ProductCommandMapper {
    public Product toDomain(ProductCommand command, Category category) {
        UUID productId = UUID.randomUUID();
        List<Part> parts = command.getParts().stream()
                .map(this::mapPart)
                .toList();

        Product product = new Product(
                productId,
                command.getName(),
                command.getDescription(),
                category,
                parts,
                null
        );

        parts.forEach(p -> p.setProduct(product));

        for (int i = 0; i < parts.size(); i++) {
            Part part = parts.get(i);
            PartDTO partDTO = command.getParts().get(i);

            for (int j = 0; j < part.getOptions().size(); j++) {
                PartOption option = part.getOptions().get(j);
                PartOptionDTO optionDTO = partDTO.getOptions().get(j);

                if (optionDTO.getConditionalPrices() != null) {
                    List<ConditionalPrice> condPrices = optionDTO.getConditionalPrices().stream()
                            .map(cpDTO -> new ConditionalPrice(
                                    mapPriceConditionRule(cpDTO.getRule(), parts),
                                    cpDTO.getPrice()
                            )).toList();
                    option.setConditionalPrices(condPrices);
                }
            }
        }

        List<Rule> rules = mapRules(command.getConfigurator().getRules(), parts);

        Configurator configurator = new Configurator(UUID.randomUUID(), product, rules, command.getConfigurator().getPriceStrategyType());
        configurator.setProduct(product);
        product.setConfigurator(configurator);

        return product;
    }

    private Part mapPart(PartDTO partDTO) {
        List<PartOption> options = partDTO.getOptions().stream()
                .map(this::mapPartOption)
                .toList();

        return new Part(
                UUID.randomUUID(),
                partDTO.getName(),
                null,
                options
        );
    }

    private PartOption mapPartOption(PartOptionDTO dto) {
        return new PartOption(
                UUID.randomUUID(),
                dto.getName(),
                dto.getPrice(),
                dto.isInStock(),
                List.of()
        );
    }

    private List<Rule> mapRules(RuleDTO ruleDTOs, List<Part> parts) {
        List<Rule> rules = new ArrayList<>();
        for (RestrictionRuleDTO restrictionRuleDTO: ruleDTOs.getRestrictionRules()) {
            rules.add(mapRestrictionRule(restrictionRuleDTO, parts));
        }
        for (PriceConditionRuleDTO priceConditionRuleDTO: ruleDTOs.getPriceConditionRules()) {
            rules.add(mapPriceConditionRule(priceConditionRuleDTO, parts));
        }
        return rules;
    }

    private RestrictionRule mapRestrictionRule(RestrictionRuleDTO dto, List<Part> parts) {
        PartOption ifOption = findOptionByName(parts, dto.getIfOption());
        List<PartOption> targets = dto.getTargetOptions().stream()
                .map(name -> findOptionByName(parts, name))
                .toList();
        return new RestrictionRule(ifOption, RuleOperator.valueOf(dto.getOperator()), targets);
    }

    private PriceConditionRule mapPriceConditionRule(PriceConditionRuleDTO dto, List<Part> parts) {
        Map<Part, PartOption> requiredOptions = new HashMap<>();
        dto.getRequiredOptions().forEach((partName, optionName) -> {
            Part part = findPartByName(parts, partName);
            PartOption option = findOptionByName(List.of(part), optionName);
            requiredOptions.put(part, option);
        });
        return new PriceConditionRule(requiredOptions);
    }
}