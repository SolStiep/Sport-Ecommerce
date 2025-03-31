package com.example.sport_ecommerce.application.model.command;

import com.example.sport_ecommerce.application.model.dto.ConfigurationDTO;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateOrderCommand {
    private String userEmail;
    private List<ConfigurationDTO> items;
}
