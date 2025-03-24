package com.example.sport_ecommerce.infrastructure.adapter.persistence.mapper;

import com.example.sport_ecommerce.domain.model.*;
import com.example.sport_ecommerce.infrastructure.adapter.persistence.jpa.OrderConfigurationEmbeddable;
import com.example.sport_ecommerce.infrastructure.adapter.persistence.jpa.OrderEntity;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class OrderEntityMapper {

    private final ObjectMapper objectMapper;

    public OrderEntity toEntity(Order order) {
        OrderEntity entity = new OrderEntity();
        entity.setId(order.getId());
        entity.setTotalPrice(order.getTotalPrice());
        return entity;
    }

    public Order toDomain(OrderEntity entity) {
        return new Order(
                null,
                null,
                entity.getTotalPrice()
        );
    }

    public List<OrderConfigurationEmbeddable> toEmbeddables(List<Configuration> configurations) {
        return configurations.stream().map(config -> {
            Map<String, String> selected = config.getSelectedOptions().entrySet().stream()
                    .collect(Collectors.toMap(
                            e -> e.getKey().getName(),
                            e -> e.getValue().getName()
                    ));
            String json;
            try {
                json = objectMapper.writeValueAsString(selected);
            } catch (JsonProcessingException e) {
                throw new RuntimeException("Error serializing selectedOptions", e);
            }
            return OrderConfigurationEmbeddable.builder()
                    .productId(config.getProduct().getId())
                    .productName(config.getProduct().getName())
                    .selectedOptionsJson(json)
                    .build();
        }).toList();
    }

    public List<Configuration> toConfigurations(List<OrderConfigurationEmbeddable> embeddables, ProductResolver resolver) {
        return embeddables.stream().map(embed -> {
            Product product = resolver.resolveProduct(embed.getProductId());

            try {
                Map<String, String> selected = objectMapper.readValue(embed.getSelectedOptionsJson(), new TypeReference<>() {});
                Map<Part, PartOption> selectedOptions = selected.entrySet().stream()
                        .collect(Collectors.toMap(
                                e -> product.getParts().stream()
                                        .filter(p -> p.getName().equalsIgnoreCase(e.getKey()))
                                        .findFirst()
                                        .orElseThrow(() -> new RuntimeException("Part not found: " + e.getKey())),
                                e -> product.getParts().stream()
                                        .flatMap(p -> p.getOptions().stream())
                                        .filter(opt -> opt.getName().equalsIgnoreCase(e.getValue()))
                                        .findFirst()
                                        .orElseThrow(() -> new RuntimeException("Option not found: " + e.getValue()))
                        ));
                return new Configuration(product, selectedOptions);
            } catch (Exception e) {
                throw new RuntimeException("Error deserializing selectedOptions", e);
            }
        }).toList();
    }
}
