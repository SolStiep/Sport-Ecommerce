package com.example.sport_ecommerce.application.mapper;

import com.example.sport_ecommerce.application.model.response.ConfigurationResponse;
import com.example.sport_ecommerce.application.model.response.SelectedOptionResponse;
import com.example.sport_ecommerce.domain.model.Configuration;
import com.example.sport_ecommerce.domain.model.Part;
import com.example.sport_ecommerce.domain.model.PartOption;
import com.example.sport_ecommerce.domain.model.service.Configurator;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Mapper(componentModel = "spring")
public interface ConfigurationResponseMapper {

    @Mapping(target = "productId", source = "product.id")
    @Mapping(target = "productName", source = ".", qualifiedByName = "getName")
    @Mapping(target = "quantity", source = "quantity")
    @Mapping(target = "preset", source = ".", qualifiedByName = "isPreset")
    @Mapping(target = "price", source = ".", qualifiedByName = "getPrice")
    @Mapping(target = "selectedOptions", source = "selectedOptions", qualifiedByName = "mapSelectedOptions")
    ConfigurationResponse toResponse(Configuration config);

    List<ConfigurationResponse> toResponseList(List<Configuration> configurations);

    @Named("mapSelectedOptions")
    static List<SelectedOptionResponse> mapSelectedOptions(Map<Part, PartOption> selected) {
        return selected.entrySet().stream()
                .map(e -> SelectedOptionResponse.builder()
                        .partId(e.getKey().getId())
                        .optionId(e.getValue().getId())
                        .price(e.getValue().getPrice())
                        .build())
                .toList();
    }

    @Named("getName")
    static String getName(Configuration config) {
        return config.getName();
    }

    @Named("isPreset")
    static boolean isPreset(Configuration config) {
        return config.isPreset();
    }

    @Named("getPrice")
    static float getPrice(Configuration config) {
        Configurator configurator = config.getProduct().getConfigurator();
        return configurator.calculatePrice(config);
    }
}
