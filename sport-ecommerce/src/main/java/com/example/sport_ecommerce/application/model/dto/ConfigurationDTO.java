package com.example.sport_ecommerce.application.model.dto;

import lombok.*;

import java.util.Map;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ConfigurationDTO {
    private UUID productId;
    private Map<UUID, UUID> selectedOptions;
}
