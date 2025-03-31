package com.example.sport_ecommerce.infrastructure.adapter.persistence.jpa;

import jakarta.persistence.*;
import lombok.*;

import java.util.Map;
import java.util.UUID;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderConfigurationEmbeddable {

    private UUID productId;
    private String productName;
    private int quantity;
    private boolean preset;
    private Float price;

    @Lob
    @Column(name = "selected_options_json", columnDefinition = "TEXT")
    private String selectedOptionsJson;

    @Transient
    private Map<String, String> selectedOptions;
}

