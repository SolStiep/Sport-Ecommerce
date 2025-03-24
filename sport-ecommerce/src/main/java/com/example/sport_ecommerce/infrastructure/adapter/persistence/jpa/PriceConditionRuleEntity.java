package com.example.sport_ecommerce.infrastructure.adapter.persistence.jpa;

import jakarta.persistence.*;
import lombok.*;

import java.util.Map;

@Entity
@DiscriminatorValue("PRICE_CONDITION")
@Getter @Setter @NoArgsConstructor
public class PriceConditionRuleEntity extends RuleEntity {

    @ElementCollection
    @CollectionTable(name = "rule_required_options", joinColumns = @JoinColumn(name = "rule_id"))
    @MapKeyColumn(name = "part")
    @Column(name = "option")
    private Map<String, String> requiredOptions;

    private float price;
}

