package com.example.sport_ecommerce.infrastructure.adapter.persistence.jpa;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@DiscriminatorValue("PRICE_CONDITION")
@Getter @Setter @NoArgsConstructor
public class PriceConditionRuleEntity extends RuleEntity {
    @ManyToOne
    @JoinColumn(name = "if_option_id", nullable = false)
    private PartOptionEntity ifOption;

    @ManyToMany
    @JoinTable(
            name = "rule_required_options",
            joinColumns = @JoinColumn(name = "rule_id"),
            inverseJoinColumns = @JoinColumn(name = "option_id")
    )
    private List<PartOptionEntity> requiredOptions;

    @Column(name = "price", nullable = false)
    private float price;
}

