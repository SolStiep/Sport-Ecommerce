package com.example.sport_ecommerce.application.mapper;

import com.example.sport_ecommerce.application.model.response.ConditionalPriceResponse;
import com.example.sport_ecommerce.application.model.response.PartOptionResponse;
import com.example.sport_ecommerce.application.model.response.PartResponse;
import com.example.sport_ecommerce.application.model.response.ProductSummaryResponse;
import com.example.sport_ecommerce.domain.model.ConditionalPrice;
import com.example.sport_ecommerce.domain.model.Part;
import com.example.sport_ecommerce.domain.model.PartOption;
import com.example.sport_ecommerce.domain.model.Product;
import com.example.sport_ecommerce.domain.model.rule.Rule;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface ProductSummaryMapper {
    @Mapping(source = "id", target = "id")
    @Mapping(source = "category.id", target = "categoryId")
    @Mapping(source = "category.name", target = "categoryName")
    ProductSummaryResponse toResponse(Product product);

    PartResponse toPartResponse(Part part);

    @Mapping(target = "conditionalPrices", source = "conditionalPrices", qualifiedByName = "mapConditionalPrices")
    PartOptionResponse toPartOptionResponse(PartOption option);

    @Named("mapConditionalPrices")
    default List<ConditionalPriceResponse> mapConditionalPrices(List<ConditionalPrice> domainList) {
        if (domainList == null || domainList.isEmpty()) {
            return List.of();
        }

        return domainList.stream()
                .filter(cp -> cp.getCondition() != null && cp.getCondition().getId() != null)
                .map(cp -> ConditionalPriceResponse.builder()
                        .ruleId(cp.getCondition().getId())
                        .price(cp.getPrice())
                        .build())
                .toList();
    }

    @Named("mapPartOptionToString")
    default String mapPartOptionToString(PartOption partOption) {
        return partOption != null ? partOption.getName() : null;
    }

    @Named("mapPartOptionsToStrings")
    default List<String> mapPartOptionsToStrings(List<PartOption> partOptions) {
        return partOptions.stream()
                .map(this::mapPartOptionToString)
                .collect(Collectors.toList());
    }

    @Named("mapRequiredOptionsToStrings")
    default Map<String, String> mapRequiredOptionsToStrings(Map<Part, PartOption> requiredOptions) {
        if (requiredOptions == null) return null;
        return requiredOptions.entrySet().stream()
                .collect(Collectors.toMap(
                        e -> e.getKey().getName(),
                        e -> e.getValue().getName()
                ));
    }
}
