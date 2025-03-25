package com.example.sport_ecommerce.application.mapper;

import com.example.sport_ecommerce.application.model.response.PresetConfigurationResponse;
import com.example.sport_ecommerce.domain.model.PresetConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface PresetConfigurationResponseMapper {

    @Mapping(source = "id", target = "id")
    @Mapping(source = "product.id", target = "productId")
    @Mapping(source = "product.name", target = "productName")
    @Mapping(source = "selectedOptions", target = "selectedOptions", qualifiedByName = "mapSelectedOptions")
    PresetConfigurationResponse toResponse(PresetConfiguration preset);

    List<PresetConfigurationResponse> toResponseList(List<PresetConfiguration> presets);

    @Named("mapSelectedOptions")
    static Map<String, String> mapSelectedOptions(Map<?, ?> selected) {
        return selected.entrySet().stream()
                .collect(Collectors.toMap(
                        e -> ((com.example.sport_ecommerce.domain.model.Part) e.getKey()).getName(),
                        e -> ((com.example.sport_ecommerce.domain.model.PartOption) e.getValue()).getName()
                ));
    }
}
