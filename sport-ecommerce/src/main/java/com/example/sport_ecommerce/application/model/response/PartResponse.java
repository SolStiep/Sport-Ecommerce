package com.example.sport_ecommerce.application.model.response;

import lombok.*;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PartResponse {
    private UUID id;
    private String name;
    private List<PartOptionResponse> options;
}
