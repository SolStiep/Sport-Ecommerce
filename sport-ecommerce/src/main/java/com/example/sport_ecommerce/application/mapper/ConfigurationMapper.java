package com.example.sport_ecommerce.application.mapper;

import com.example.sport_ecommerce.application.model.dto.ConfigurationDTO;
import com.example.sport_ecommerce.application.port.out.ProductRepositoryPort;
import com.example.sport_ecommerce.domain.model.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class ConfigurationMapper {
    private final ProductRepositoryPort productRepository;

    public Configuration toDomain(ConfigurationDTO dto) {
        Product product = productRepository.findById(dto.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found"));

        Map<Part, PartOption> selected = new HashMap<>();

        dto.getSelectedOptions().forEach((partId, optionId) -> {
            Part part = product.getParts().stream()
                    .filter(p -> p.getId().equals(partId))
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("Part not found: " + partId));

            PartOption option = part.getOptions().stream()
                    .filter(o -> o.getId().equals(optionId))
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("Option not found for part " + partId + ": " + optionId));

            selected.put(part, option);
        });

        if (dto.isPreset()) {
            return new PresetConfiguration(
                    product,
                    selected,
                    dto.getPresetName(),
                    dto.getPresetPrice(),
                    true,
                    dto.getQuantity()
            );
        }

        return new Configuration(product, selected, dto.getQuantity());
    }

    public List<Configuration> toDomainList(List<ConfigurationDTO> dtos) {
        return dtos.stream().map(this::toDomain).toList();
    }
}
