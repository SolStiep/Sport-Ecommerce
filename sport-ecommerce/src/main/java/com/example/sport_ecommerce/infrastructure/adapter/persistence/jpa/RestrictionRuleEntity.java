package com.example.sport_ecommerce.infrastructure.adapter.persistence.jpa;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@DiscriminatorValue("RESTRICTION")
@Getter @Setter @NoArgsConstructor
public class RestrictionRuleEntity extends RuleEntity {

    private String ifOption;
    private String operator;

    @ElementCollection
    @CollectionTable(name = "rule_target_options", joinColumns = @JoinColumn(name = "rule_id"))
    @Column(name = "target_option")
    private List<String> targetOptions;
}

