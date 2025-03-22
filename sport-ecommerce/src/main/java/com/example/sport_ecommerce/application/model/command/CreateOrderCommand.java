package com.example.sport_ecommerce.application.model.command;

import com.example.sport_ecommerce.application.model.dto.ConfigurationDTO;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateOrderCommand {
    private UUID userId;
    private List<ConfigurationDTO> items;
}
