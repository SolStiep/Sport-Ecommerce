package com.example.sport_ecommerce.application.mapper;

import com.example.sport_ecommerce.application.model.response.ConfigurationResponse;
import com.example.sport_ecommerce.application.model.response.SelectedOptionResponse;
import com.example.sport_ecommerce.domain.model.Configuration;
import com.example.sport_ecommerce.domain.model.Part;
import com.example.sport_ecommerce.domain.model.PartOption;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;
import java.util.Map;

@Mapper(componentModel = "spring")
public interface ConfigurationResponseMapper {

    @Mapping(target = "selectedOptions", source = "selectedOptions", qualifiedByName = "mapSelectedOptions")
    ConfigurationResponse toResponse(Configuration config);

    List<ConfigurationResponse> toResponseList(List<Configuration> configurations);

    @Named("mapSelectedOptions")
    static List<SelectedOptionResponse> mapSelectedOptions(Map<?, ?> selected) {
        return selected.entrySet().stream()
                .map(e -> SelectedOptionResponse.builder()
                        .partName(((Part) e.getKey()).getName())
                        .optionName(((PartOption) e.getValue()).getName())
                        .price(((PartOption) e.getValue()).getPrice())
                        .build())
                .toList();
    }
}
