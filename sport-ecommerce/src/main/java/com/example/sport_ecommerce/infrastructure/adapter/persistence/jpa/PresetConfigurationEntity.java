package com.example.sport_ecommerce.infrastructure.adapter.persistence.jpa;

import jakarta.persistence.*;
import lombok.*;

import java.util.Map;
import java.util.UUID;

@Entity
@Table(name = "preset_configurations",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "name")
        })
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PresetConfigurationEntity {
    @Id
    @GeneratedValue
    private UUID id;

    private String name;

    private float price;

    private boolean isActive;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private ProductEntity product;

    @ElementCollection
    @CollectionTable(name = "preset_selected_options", joinColumns = @JoinColumn(name = "preset_id"))
    @MapKeyColumn(name = "part_name")
    @Column(name = "option_name")
    private Map<String, String> selectedOptions;
}
