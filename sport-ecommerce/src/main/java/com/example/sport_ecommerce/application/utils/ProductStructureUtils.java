package com.example.sport_ecommerce.application.utils;

import com.example.sport_ecommerce.domain.model.Part;
import com.example.sport_ecommerce.domain.model.PartOption;

import java.util.List;
import java.util.UUID;

public class ProductStructureUtils {
    private ProductStructureUtils() {}

    public static Part findPartByName(List<Part> parts, String name) {
        return parts.stream()
                .filter(p -> p.getName().equalsIgnoreCase(name))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Part not found: " + name));
    }

    public static PartOption findOptionByName(List<Part> parts, String name) {
        return parts.stream()
                .flatMap(p -> p.getOptions().stream())
                .filter(opt -> opt.getName().equalsIgnoreCase(name))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Option not found: " + name));
    }

    public static PartOption findOptionById(List<Part> parts, UUID id) {
        return parts.stream()
                .flatMap(p -> p.getOptions().stream())
                .filter(opt -> opt.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Option not found: " + id));
    }
}
