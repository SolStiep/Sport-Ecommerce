package com.example.sport_ecommerce.infrastructure.adapter.persistence.jpa;

import com.example.sport_ecommerce.domain.model.rule.Rule;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "conditional_prices")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ConditionalPriceEntity {
    @Id
    @GeneratedValue
    private UUID id;

    private float price;

    @ManyToOne
    @JoinColumn(name = "part_option_id")
    private PartOptionEntity partOption;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "rule_id")
    private PriceConditionRuleEntity condition;

}

