package com.example.sport_ecommerce.application.model.command;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryCommand {
    private UUID id;
    private String name;
    private String description;
}
