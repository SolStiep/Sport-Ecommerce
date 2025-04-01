package com.example.sport_ecommerce.infrastructure.adapter.persistence.mapper;

import com.example.sport_ecommerce.domain.model.PartOption;
import com.example.sport_ecommerce.infrastructure.adapter.persistence.jpa.*;
import org.mapstruct.Mapper;

import java.util.ArrayList;

@Mapper(componentModel = "spring")
public abstract class PartOptionEntityMapper {
    public PartOptionEntity toEntity(PartOption option, PartEntity parentPart) {
        return PartOptionEntity.builder()
                .name(option.getName())
                .price(option.getPrice())
                .inStock(option.isInStock())
                .part(parentPart)
                .build();
    }

    public PartOption toDomain(PartOptionEntity entity) {
        return new PartOption(
                entity.getId(),
                entity.getName(),
                entity.getPrice(),
                entity.isInStock(),
                new ArrayList<>()
        );
    }

}
