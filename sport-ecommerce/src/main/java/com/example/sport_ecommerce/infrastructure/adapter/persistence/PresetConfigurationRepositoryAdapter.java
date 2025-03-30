package com.example.sport_ecommerce.infrastructure.adapter.persistence;

import com.example.sport_ecommerce.application.port.out.PresetConfigurationRepositoryPort;
import com.example.sport_ecommerce.application.port.out.ProductRepositoryPort;
import com.example.sport_ecommerce.domain.model.Part;
import com.example.sport_ecommerce.domain.model.PartOption;
import com.example.sport_ecommerce.domain.model.PresetConfiguration;
import com.example.sport_ecommerce.domain.model.Product;
import com.example.sport_ecommerce.infrastructure.adapter.persistence.jpa.PresetConfigurationEntity;
import com.example.sport_ecommerce.infrastructure.adapter.persistence.jpa.ProductEntity;
import com.example.sport_ecommerce.infrastructure.adapter.persistence.mapper.PresetConfigurationEntityMapper;
import com.example.sport_ecommerce.infrastructure.adapter.persistence.repository.PresetConfigurationJpaRepository;
import com.example.sport_ecommerce.infrastructure.adapter.persistence.repository.ProductJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@RequiredArgsConstructor
public class PresetConfigurationRepositoryAdapter implements PresetConfigurationRepositoryPort {

    private final PresetConfigurationJpaRepository jpaRepository;
    private final ProductJpaRepository productJpaRepository;
    private final PresetConfigurationEntityMapper mapper;
    private final ProductRepositoryPort productRepository;

    @Override
    public Optional<PresetConfiguration> findById(UUID presetId) {
        return jpaRepository.findById(presetId)
                .map(this::mapBackWithProduct);
    }

    @Override
    public List<PresetConfiguration> findAll() {
        return jpaRepository.findAll().stream()
                .map(this::mapBackWithProduct)
                .toList();
    }

    @Override
    public PresetConfiguration save(PresetConfiguration preset) {
        ProductEntity fullProduct = productJpaRepository.findById(preset.getProduct().getId())
                .orElseThrow(() -> new RuntimeException("Product not found"));
        PresetConfigurationEntity entity = mapper.toEntity(preset);
        entity.setProduct(fullProduct);
        PresetConfigurationEntity saved = jpaRepository.save(entity);
        return mapBackWithProduct(saved);
    }

    @Override
    public void deleteById(UUID presetId) {
        jpaRepository.deleteById(presetId);
    }

    @Override
    public List<PresetConfiguration> findByProductId(UUID productId) {
        return jpaRepository.findByProductId(productId).stream()
                .map(this::mapBackWithProduct)
                .toList();
    }

    private PresetConfiguration mapBackWithProduct(PresetConfigurationEntity entity) {
        Product product = productRepository.findById(entity.getProduct().getId())
                .orElseThrow(() -> new RuntimeException("Product not found"));

        PresetConfiguration preset = mapper.toDomain(entity);

        Map<Part, PartOption> selected = new HashMap<>();
        for (Map.Entry<String, String> entry : entity.getSelectedOptions().entrySet()) {
            Part part = product.getParts().stream()
                    .filter(p -> p.getName().equalsIgnoreCase(entry.getKey()))
                    .findFirst().orElseThrow();

            PartOption option = part.getOptions().stream()
                    .filter(o -> o.getName().equalsIgnoreCase(entry.getValue()))
                    .findFirst().orElseThrow();

            selected.put(part, option);
        }

        preset.setProduct(product);
        preset.setSelectedOptions(selected);
        return preset;
    }
}

