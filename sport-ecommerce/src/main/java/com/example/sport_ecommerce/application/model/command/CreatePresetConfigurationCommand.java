package com.example.sport_ecommerce.application.model.command;

import lombok.*;

import java.util.Map;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreatePresetConfigurationCommand {
    private UUID productId;
    private String name;
    private boolean active;
    private Float price;
    private Map<UUID, UUID> selectedOptions;
}
