package com.example.sport_ecommerce.infrastructure.adapter.persistence.jpa;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "part_options")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PartOptionEntity {
    @Id
    @GeneratedValue
    private UUID id;

    private String name;
    private float price;
    private boolean inStock;

    @ManyToOne
    @JoinColumn(name = "part_id")
    private PartEntity part;

    @OneToMany(mappedBy = "partOption", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ConditionalPriceEntity> conditionalPrices;

}
