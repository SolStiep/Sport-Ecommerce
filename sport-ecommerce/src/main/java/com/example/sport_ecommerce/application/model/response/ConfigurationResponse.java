package com.example.sport_ecommerce.application.model.response;

import lombok.*;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ConfigurationResponse {
    private UUID productId;
    private String productName;
    private List<SelectedOptionResponse> selectedOptions;
}
