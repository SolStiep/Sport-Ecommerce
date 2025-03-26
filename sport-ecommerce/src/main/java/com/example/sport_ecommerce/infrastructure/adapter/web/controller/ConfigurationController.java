package com.example.sport_ecommerce.infrastructure.adapter.web.controller;

import com.example.sport_ecommerce.application.model.dto.ConfigurationDTO;
import com.example.sport_ecommerce.application.model.response.PrepareConfigurationResponse;
import com.example.sport_ecommerce.application.port.in.configuration.PrepareConfigurationUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/configurations")
@RequiredArgsConstructor
public class ConfigurationController {

    private final PrepareConfigurationUseCase prepareUseCase;

    @PostMapping("/prepare")
    public ResponseEntity<PrepareConfigurationResponse> prepareConfiguration(
            @RequestBody @Valid ConfigurationDTO dto) {

        PrepareConfigurationResponse response = prepareUseCase.prepare(dto);
        return ResponseEntity.ok(response);
    }
}
