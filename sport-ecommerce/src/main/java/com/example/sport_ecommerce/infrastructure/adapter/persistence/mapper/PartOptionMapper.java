package com.example.sport_ecommerce.infrastructure.adapter.persistence.mapper;

import com.example.sport_ecommerce.domain.model.ConditionalPrice;
import com.example.sport_ecommerce.domain.model.PartOption;
import com.example.sport_ecommerce.infrastructure.adapter.persistence.jpa.ConditionalPriceEntity;
import com.example.sport_ecommerce.infrastructure.adapter.persistence.jpa.PartEntity;
import com.example.sport_ecommerce.infrastructure.adapter.persistence.jpa.PartOptionEntity;
import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Mapper(componentModel = "spring", uses = { ConditionalPriceMapper.class })
public abstract class PartOptionMapper {

    @Autowired
    protected ConditionalPriceMapper conditionalPriceMapper;

    public PartOptionEntity toEntity(PartOption option, PartEntity parentPart) {
        List<ConditionalPriceEntity> condEntities = option.getConditionalPrices().stream()
                .map(conditionalPriceMapper::toEntity)
                .toList();

        PartOptionEntity entity = PartOptionEntity.builder()
                .id(option.getId())
                .name(option.getName())
                .price(option.getPrice())
                .inStock(option.isInStock())
                .conditionalPrices(condEntities)
                .part(parentPart)
                .build();

        condEntities.forEach(cp -> cp.setPartOption(entity));
        return entity;
    }

    public PartOption toDomain(PartOptionEntity entity) {
        List<ConditionalPrice> conditionalPrices = entity.getConditionalPrices() != null ?
                entity.getConditionalPrices().stream()
                        .map(cp -> conditionalPriceMapper.toDomain(cp))
                        .toList() : List.of();

        return new PartOption(
                entity.getId(),
                entity.getName(),
                entity.getPrice(),
                entity.isInStock(),
                conditionalPrices
        );
    }
}
