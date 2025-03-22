package com.example.sport_ecommerce.application.model.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PartDTO {
    private String name;
    private List<PartOptionDTO> options;
}
