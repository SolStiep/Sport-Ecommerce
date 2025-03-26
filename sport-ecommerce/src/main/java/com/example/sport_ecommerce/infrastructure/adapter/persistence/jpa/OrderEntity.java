package com.example.sport_ecommerce.infrastructure.adapter.persistence.jpa;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "orders")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderEntity {
    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    private UserEntity user;

    private float totalPrice;

    @ElementCollection
    @CollectionTable(name = "order_configurations", joinColumns = @JoinColumn(name = "order_id"))
    private List<OrderConfigurationEmbeddable> items;
}

