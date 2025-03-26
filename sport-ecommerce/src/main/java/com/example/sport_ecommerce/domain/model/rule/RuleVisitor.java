package com.example.sport_ecommerce.domain.model.rule;

public interface RuleVisitor {
    void visit(RestrictionRule restrictionRule);
    void visit(PriceConditionRule priceConditionRule);
}

