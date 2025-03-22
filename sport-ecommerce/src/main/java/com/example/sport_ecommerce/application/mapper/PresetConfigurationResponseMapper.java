package com.example.sport_ecommerce.application.mapper;

import com.example.sport_ecommerce.application.model.response.PresetConfigurationResponse;
import com.example.sport_ecommerce.domain.model.PresetConfiguration;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class PresetConfigurationResponseMapper {
    public PresetConfigurationResponse toResponse(PresetConfiguration preset) {
        Map<String, String> selected = preset.getSelectedOptions().entrySet().stream()
                .collect(Collectors.toMap(
                        e -> e.getKey().getName(),
                        e -> e.getValue().getName()
                ));

        return PresetConfigurationResponse.builder()
                .id(preset.getId())
                .productId(preset.getProduct().getId())
                .productName(preset.getProduct().getName())
                .name(preset.getName())
                .active(preset.isActive())
                .price(preset.getPrice())
                .selectedOptions(selected)
                .build();
    }

    public List<PresetConfigurationResponse> toResponseList(List<PresetConfiguration> presets) {
        return presets.stream().map(this::toResponse).toList();
    }
}
