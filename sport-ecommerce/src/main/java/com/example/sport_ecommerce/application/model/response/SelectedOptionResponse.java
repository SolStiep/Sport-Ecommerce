package com.example.sport_ecommerce.application.model.response;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SelectedOptionResponse {
    private UUID partId;
    private UUID optionId;
    private float price;
}
