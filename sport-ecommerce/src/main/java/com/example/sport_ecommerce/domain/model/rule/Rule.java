package com.example.sport_ecommerce.domain.model.rule;

import com.example.sport_ecommerce.domain.model.Configuration;

import java.util.UUID;

public interface Rule {
    boolean isSatisfied(Configuration config);
    boolean isValid(Configuration config);
    void accept(RuleVisitor visitor);

    UUID getId();
}
