package com.example.sport_ecommerce.infrastructure.adapter.persistence.mapper;

import com.example.sport_ecommerce.domain.model.*;
import com.example.sport_ecommerce.domain.model.service.Configurator;
import com.example.sport_ecommerce.infrastructure.adapter.persistence.jpa.*;
import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Mapper(componentModel = "spring")
public abstract class ProductEntityMapper {

    @Autowired
    protected PartOptionMapper partOptionMapper;

    @Autowired
    protected ConditionalPriceMapper conditionalPriceMapper;

    @Autowired
    protected ConfiguratorEntityMapper configuratorMapper;

    public ProductEntity toEntity(Product product) {
        if (product == null) return null;

        ProductEntity entity = ProductEntity.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .build();

        if (product.getCategory() != null) {
            entity.setCategory(CategoryEntity.builder()
                    .id(product.getCategory().getId())
                    .name(product.getCategory().getName())
                    .description(product.getCategory().getDescription())
                    .build());
        }

        if (product.getParts() != null) {
            List<PartEntity> partEntities = product.getParts().stream().map(part -> {
                PartEntity partEntity = PartEntity.builder()
                        .id(part.getId())
                        .name(part.getName())
                        .product(entity)
                        .build();

                if (part.getOptions() != null) {
                    List<PartOptionEntity> optionEntities = part.getOptions().stream()
                            .map(option -> partOptionMapper.toEntity(option, partEntity))
                            .toList();
                    partEntity.setOptions(optionEntities);
                }

                return partEntity;
            }).toList();

            entity.setParts(partEntities);
        }

        if (product.getConfigurator() != null) {
            ConfiguratorEntity configuratorEntity = configuratorMapper.toEntity(product.getConfigurator(), entity);
            configuratorEntity.setProduct(entity);
            entity.setConfigurator(configuratorEntity);
        }

        return entity;
    }

    public Product toDomain(ProductEntity entity) {
        if (entity == null) return null;

        Product product = new Product(
                entity.getId(),
                entity.getName(),
                entity.getDescription(),
                null,
                new ArrayList<>(),
                null
        );

        if (entity.getCategory() != null) {
            product.setCategory(new Category(
                    entity.getCategory().getId(),
                    entity.getCategory().getName(),
                    entity.getCategory().getDescription()
            ));
        }

        if (entity.getParts() != null) {
            List<Part> parts = new ArrayList<>();
            Map<PartEntity, Part> partEntityMap = new HashMap<>();

            for (PartEntity partEntity : entity.getParts()) {
                Part part = new Part(
                        partEntity.getId(),
                        partEntity.getName(),
                        product,
                        new ArrayList<>()
                );

                parts.add(part);
                partEntityMap.put(partEntity, part);
            }

            product.setParts(parts);

            for (PartEntity partEntity : entity.getParts()) {
                Part part = partEntityMap.get(partEntity);
                List<PartOption> options = getPartOptions(partEntity);

                part.setOptions(options);
            }

            for (PartEntity partEntity : entity.getParts()) {
                Part part = partEntityMap.get(partEntity);

                for (PartOption option : part.getOptions()) {
                    PartOptionEntity optEntity = partEntity.getOptions().stream()
                            .filter(e -> e.getId().equals(option.getId()))
                            .findFirst().orElseThrow();

                    if (optEntity.getConditionalPrices() != null) {
                        List<ConditionalPrice> condPrices = optEntity.getConditionalPrices().stream()
                                .map(cp -> conditionalPriceMapper.toDomain(cp))
                                .toList();
                        option.setConditionalPrices(condPrices);
                    }
                }
            }
        }

        if (entity.getConfigurator() != null) {
            Configurator configurator = configuratorMapper.toDomain(entity.getConfigurator());
            configurator.setProduct(product);
            product.setConfigurator(configurator);
        }

        return product;
    }

    private List<PartOption> getPartOptions(PartEntity partEntity) {
        List<PartOption> options = new ArrayList<>();

        for (PartOptionEntity optEntity : partEntity.getOptions()) {
            PartOption option = new PartOption(
                    optEntity.getId(),
                    optEntity.getName(),
                    optEntity.getPrice(),
                    optEntity.isInStock(),
                    new ArrayList<>()
            );
            options.add(option);
        }
        return options;
    }
}


