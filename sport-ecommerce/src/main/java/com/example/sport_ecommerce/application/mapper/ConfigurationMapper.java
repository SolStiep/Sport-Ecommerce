package com.example.sport_ecommerce.application.mapper;

import com.example.sport_ecommerce.application.model.dto.ConfigurationDTO;
import com.example.sport_ecommerce.application.port.out.ProductRepositoryPort;
import com.example.sport_ecommerce.domain.model.Configuration;
import com.example.sport_ecommerce.domain.model.Part;
import com.example.sport_ecommerce.domain.model.PartOption;
import com.example.sport_ecommerce.domain.model.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static com.example.sport_ecommerce.application.utils.ProductStructureUtils.findOptionByName;
import static com.example.sport_ecommerce.application.utils.ProductStructureUtils.findPartByName;

@Component
@RequiredArgsConstructor
public class ConfigurationMapper {
    private final ProductRepositoryPort productRepository;

    public Configuration toDomain(ConfigurationDTO dto) {
        Product product = productRepository.findById(dto.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found"));

        Map<Part, PartOption> selected = new HashMap<>();

        dto.getSelectedOptions().forEach((partName, optionName) -> {
            Part part = findPartByName(product.getParts(), partName);
            PartOption option = findOptionByName(product.getParts(), optionName);
            selected.put(part, option);
        });

        return new Configuration(UUID.randomUUID(), product, selected);
    }

    public List<Configuration> toDomainList(List<ConfigurationDTO> dtos) {
        return dtos.stream().map(this::toDomain).toList();
    }
}
