package com.example.sport_ecommerce.application.mapper;

import com.example.sport_ecommerce.application.model.response.PrepareConfigurationResponse;
import com.example.sport_ecommerce.application.model.response.SelectedOptionResponse;
import com.example.sport_ecommerce.domain.model.Configuration;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PrepareConfigurationResponseMapper {
    public PrepareConfigurationResponse toResponse(Configuration config, float price, boolean valid) {
        List<SelectedOptionResponse> selected = config.getSelectedOptions().entrySet().stream()
                .map(entry -> SelectedOptionResponse.builder()
                        .partName(entry.getKey().getName())
                        .optionName(entry.getValue().getName())
                        .price(entry.getValue().getPrice())
                        .build())
                .toList();

        return PrepareConfigurationResponse.builder()
                .productId(config.getProduct().getId())
                .productName(config.getProduct().getName())
                .totalPrice(price)
                .valid(valid)
                .selectedOptions(selected)
                .incompatibleOptions(List.of()) // TODO: calculate restrictions
                .build();
    }
}
