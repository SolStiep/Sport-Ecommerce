package com.example.sport_ecommerce.application.mapper;

import com.example.sport_ecommerce.application.model.response.PresetCatalogResponse;
import com.example.sport_ecommerce.application.model.response.PresetConfigurationResponse;
import com.example.sport_ecommerce.domain.model.Part;
import com.example.sport_ecommerce.domain.model.PartOption;
import com.example.sport_ecommerce.domain.model.PresetConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public abstract class PresetConfigurationResponseMapper {

    public PresetConfigurationResponse toConfigurationResponse(PresetConfiguration preset) {
        return PresetConfigurationResponse.builder()
                .id(preset.getId())
                .name(preset.getName())
                .price(preset.getPrice())
                .active(preset.isActive())
                .productId(preset.getProduct().getId())
                .productName(preset.getProduct().getName())
                .selectedOptions(mapSelectedOptions(preset))
                .build();
    }

    public List<PresetConfigurationResponse> toConfigurationResponseList(List<PresetConfiguration> presets) {
        return presets.stream().map(this::toConfigurationResponse).toList();
    }

    public PresetCatalogResponse toCatalogResponse(PresetConfiguration preset) {
        return PresetCatalogResponse.builder()
                .id(preset.getId())
                .name(preset.getName())
                .price(preset.getPrice())
                .active(preset.isActive())
                .selectedOptions(mapSelectedOptions(preset))
                .product(PresetCatalogResponse.ProductInfo.builder()
                        .id(preset.getProduct().getId())
                        .name(preset.getProduct().getName())
                        .category(PresetCatalogResponse.CategoryInfo.builder()
                                .id(preset.getProduct().getCategory().getId())
                                .name(preset.getProduct().getCategory().getName())
                                .build())
                        .build())
                .build();
    }

    public List<PresetCatalogResponse> toCatalogResponseList(List<PresetConfiguration> presets) {
        return presets.stream().map(this::toCatalogResponse).toList();
    }

    private Map<String, String> mapSelectedOptions(PresetConfiguration preset) {
        return preset.getSelectedOptions().entrySet().stream()
                .collect(Collectors.toMap(
                        e -> e.getKey().getName(),
                        e -> e.getValue().getName()
                ));
    }
}
