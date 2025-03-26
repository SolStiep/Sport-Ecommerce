package com.example.sport_ecommerce.infrastructure.adapter.persistence.jpa;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@DiscriminatorValue("RESTRICTION")
@Getter @Setter @NoArgsConstructor
public class RestrictionRuleEntity extends RuleEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "if_option_id", nullable = false)
    private PartOptionEntity ifOption;

    @ManyToMany
    @JoinTable(
            name = "rule_target_options",
            joinColumns = @JoinColumn(name = "rule_id"),
            inverseJoinColumns = @JoinColumn(name = "target_option_id")
    )
    private List<PartOptionEntity> targetOptions;

    @Column(name = "operator", nullable = false)
    private String operator;
}

