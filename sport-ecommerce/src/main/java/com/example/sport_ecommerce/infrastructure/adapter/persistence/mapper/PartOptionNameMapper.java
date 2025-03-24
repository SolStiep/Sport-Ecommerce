package com.example.sport_ecommerce.infrastructure.adapter.persistence.mapper;

import com.example.sport_ecommerce.domain.model.PartOption;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class PartOptionNameMapper {

    @Named("toName")
    public String toName(PartOption option) {
        return option != null ? option.getName() : null;
    }

    @Named("fromName")
    public PartOption fromName(String name) {
        if (name == null) return null;
        return new PartOption(UUID.randomUUID(), name, 0f, true, List.of());
    }

    @Named("toNames")
    public List<String> toNames(List<PartOption> options) {
        if (options == null) return List.of();
        return options.stream()
                .map(this::toName)
                .toList();
    }

    @Named("fromNames")
    public List<PartOption> fromNames(List<String> names) {
        if (names == null) return List.of();
        return names.stream()
                .map(this::fromName)
                .toList();
    }
}

