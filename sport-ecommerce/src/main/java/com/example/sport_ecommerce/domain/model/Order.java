package com.example.sport_ecommerce.domain.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Setter
@Getter
@NoArgsConstructor
public class Order {
    private UUID id;
    private User user;
    private List<Configuration> items;
    private float totalPrice;

    public Order(User user, List<Configuration> items, float totalPrice) {
        this.user = user;
        this.items = items;
        this.totalPrice = totalPrice;
    }
}
