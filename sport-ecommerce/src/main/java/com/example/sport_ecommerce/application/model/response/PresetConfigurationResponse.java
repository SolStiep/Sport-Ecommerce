package com.example.sport_ecommerce.application.model.response;

import lombok.*;

import java.util.Map;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PresetConfigurationResponse {
    private UUID id;
    private UUID productId;
    private String productName;
    private String name;
    private boolean active;
    private float price;
    private Map<String, String> selectedOptions;
}
