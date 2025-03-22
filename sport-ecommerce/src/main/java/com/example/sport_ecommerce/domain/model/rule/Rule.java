package com.example.sport_ecommerce.domain.model.rule;

import com.example.sport_ecommerce.domain.model.Configuration;

public interface Rule {
    boolean isSatisfied(Configuration config);
}
