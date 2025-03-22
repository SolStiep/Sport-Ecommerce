package com.example.sport_ecommerce.application.mapper;

import com.example.sport_ecommerce.application.model.command.ProductCommand;
import com.example.sport_ecommerce.application.model.dto.RuleDTO;
import com.example.sport_ecommerce.domain.model.Category;
import com.example.sport_ecommerce.domain.model.Part;
import com.example.sport_ecommerce.domain.model.PartOption;
import com.example.sport_ecommerce.domain.model.Product;
import com.example.sport_ecommerce.domain.model.rule.PriceConditionRule;
import com.example.sport_ecommerce.domain.model.rule.RestrictionRule;
import com.example.sport_ecommerce.domain.model.rule.Rule;
import com.example.sport_ecommerce.domain.model.service.Configurator;
import com.example.sport_ecommerce.domain.model.strategy.PriceStrategy;
import com.example.sport_ecommerce.domain.model.strategy.SimplePriceStrategy;
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
                .map(partDTO -> new Part(
                        UUID.randomUUID(),
                        partDTO.getName(),
                        null,
                        partDTO.getOptions().stream()
                                .map(opt -> new PartOption(
                                        UUID.randomUUID(),
                                        opt.getName(),
                                        opt.getPrice(),
                                        opt.isInStock(),
                                        new ArrayList<>()
                                )).toList()
                )).toList();

        Product product = new Product(
                productId,
                command.getName(),
                command.getDescription(),
                category,
                parts,
                null
        );

        parts.forEach(p -> p.setProduct(product));

        List<Rule> rules = mapRules(command.getConfigurator().getRules(), parts);
        PriceStrategy strategy = mapStrategy(command.getConfigurator().getPriceStrategy());

        Configurator configurator = new Configurator(UUID.randomUUID(), product, rules, strategy);
        product.setConfigurator(configurator);

        return product;
    }

    private List<Rule> mapRules(List<RuleDTO> ruleDTOs, List<Part> parts) {
        List<Rule> rules = new ArrayList<>();
        for (RuleDTO dto : ruleDTOs) {
            switch (dto.getType()) {
                case RESTRICTION -> {
                    PartOption ifOption = findOptionByName(parts, dto.getIfOption());
                    List<PartOption> targets = dto.getTargetOptions().stream()
                            .map(name -> findOptionByName(parts, name)).toList();
                    rules.add(new RestrictionRule(ifOption, RuleOperator.valueOf(dto.getOperator()), targets));
                }
                case PRICE_CONDITION -> {
                    Map<Part, PartOption> requiredOptions = new HashMap<>();
                    dto.getRequiredOptions().forEach((partName, optionName) -> {
                        Part part = findPartByName(parts, partName);
                        PartOption option = findOptionByName(List.of(part), optionName);
                        requiredOptions.put(part, option);
                    });
                    rules.add(new PriceConditionRule(requiredOptions));
                }
            }
        }
        return rules;
    }

    private PriceStrategy mapStrategy(String strategy) {
        return new SimplePriceStrategy();
    }
}
