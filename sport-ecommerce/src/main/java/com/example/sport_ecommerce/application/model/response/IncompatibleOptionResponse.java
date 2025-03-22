package com.example.sport_ecommerce.application.model.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class IncompatibleOptionResponse {
    private String partName;
    private String optionName;
}
