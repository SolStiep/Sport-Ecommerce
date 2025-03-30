package com.example.sport_ecommerce.application.model.command;

import com.example.sport_ecommerce.application.model.dto.ConfiguratorDTO;
import com.example.sport_ecommerce.application.model.dto.PartDTO;
import lombok.*;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductCommand {
    private UUID id;
    private String name;
    private String description;
    private UUID categoryId;
    private List<PartDTO> parts;
}
