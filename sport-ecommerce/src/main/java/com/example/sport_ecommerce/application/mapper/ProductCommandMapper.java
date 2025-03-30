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
}
