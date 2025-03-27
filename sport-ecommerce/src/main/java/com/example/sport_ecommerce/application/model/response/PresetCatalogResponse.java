package com.example.sport_ecommerce.application.model.response;

import lombok.*;

import java.util.Map;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PresetCatalogResponse {
    private UUID id;
    private String name;
    private float price;
    private boolean active;
    private Map<String, String> selectedOptions;
    private ProductInfo product;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class ProductInfo {
        private UUID id;
        private String name;
        private CategoryInfo category;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class CategoryInfo {
        private UUID id;
        private String name;
    }
}
