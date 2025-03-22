package com.example.sport_ecommerce.application.mapper;

import com.example.sport_ecommerce.application.model.command.CreatePresetConfigurationCommand;
import com.example.sport_ecommerce.application.port.out.ProductRepositoryPort;
import com.example.sport_ecommerce.domain.model.Part;
import com.example.sport_ecommerce.domain.model.PartOption;
import com.example.sport_ecommerce.domain.model.PresetConfiguration;
import com.example.sport_ecommerce.domain.model.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class PresetConfigurationMapper {
    private final ProductRepositoryPort productRepository;

    public PresetConfiguration toDomain(CreatePresetConfigurationCommand command) {
        Product product = productRepository.findById(command.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found"));

        Map<Part, PartOption> selected = new HashMap<>();

        command.getSelectedOptions().forEach((partName, optionName) -> {
            Part part = product.getParts().stream()
                    .filter(p -> p.getName().equalsIgnoreCase(partName))
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("Part not found: " + partName));

            PartOption option = part.getOptions().stream()
                    .filter(o -> o.getName().equalsIgnoreCase(optionName))
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("Option not found: " + optionName));

            selected.put(part, option);
        });

        PresetConfiguration preset = new PresetConfiguration(
                UUID.randomUUID(),
                product,
                selected,
                command.getName(),
                null,
                command.isActive()
        );

        preset.setPrice(command.getPrice() != null ? command.getPrice() : product.getConfigurator().calculatePrice(preset));

        return preset;
    }
}
