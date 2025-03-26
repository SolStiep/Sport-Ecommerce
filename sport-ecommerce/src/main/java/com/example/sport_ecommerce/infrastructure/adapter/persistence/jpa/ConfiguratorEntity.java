package com.example.sport_ecommerce.infrastructure.adapter.persistence.jpa;

import com.example.sport_ecommerce.domain.model.valueobject.PriceStrategyType;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "configurators")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ConfiguratorEntity {
    @Id
    @GeneratedValue
    private UUID id;

    @OneToOne
    @JoinColumn(name = "product_id")
    private ProductEntity product;

    @OneToMany(mappedBy = "configurator", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RuleEntity> rules;

    @Enumerated(EnumType.STRING)
    private PriceStrategyType priceStrategyType;
}

