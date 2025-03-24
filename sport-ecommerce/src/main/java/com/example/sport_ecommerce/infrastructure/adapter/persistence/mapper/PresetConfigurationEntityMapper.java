package com.example.sport_ecommerce.infrastructure.adapter.persistence.mapper;

import com.example.sport_ecommerce.domain.model.Part;
import com.example.sport_ecommerce.domain.model.PartOption;
import com.example.sport_ecommerce.domain.model.PresetConfiguration;
import com.example.sport_ecommerce.infrastructure.adapter.persistence.jpa.PresetConfigurationEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface PresetConfigurationEntityMapper {

    @Mapping(target = "product", ignore = true)
    @Mapping(target = "selectedOptions", qualifiedByName = "mapToDomainSelectedOptions")
    PresetConfiguration toDomain(PresetConfigurationEntity entity);

    @Mapping(target = "product", ignore = true)
    @Mapping(target = "selectedOptions", qualifiedByName = "mapToEntitySelectedOptions")
    PresetConfigurationEntity toEntity(PresetConfiguration preset);

    @Named("mapToDomainSelectedOptions")
    static Map<Part, PartOption> mapToDomain(Map<String, String> selected) {
        return new HashMap<>();
    }

    @Named("mapToEntitySelectedOptions")
    static Map<String, String> mapToEntity(Map<Part, PartOption> selected) {
        if (selected == null) return Map.of();
        return selected.entrySet().stream()
                .collect(Collectors.toMap(
                        e -> e.getKey().getName(),
                        e -> e.getValue().getName()
                ));
    }
}

