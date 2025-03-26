package com.example.sport_ecommerce.infrastructure.adapter.persistence.jpa;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "rule_type")
@Getter @Setter @NoArgsConstructor
public abstract class RuleEntity {
    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "configurator_id")
    private ConfiguratorEntity configurator;
}
