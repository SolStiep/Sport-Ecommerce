package com.example.sport_ecommerce.application.model.response;

import java.util.List;
import java.util.UUID;

import com.example.sport_ecommerce.application.model.dto.PriceConditionRuleDTO;
import com.example.sport_ecommerce.application.model.dto.RestrictionRuleDTO;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PrepareConfigurationResponse {
    private UUID productId;
    private String productName;
    private float totalPrice;
    private boolean valid;
    private List<SelectedOptionResponse> selectedOptions;
    private List<PriceConditionRuleDTO> priceViolations;
    private List<RestrictionRuleDTO> restrictionViolations;
}
