package com.example.sport_ecommerce.infrastructure.adapter.web.controller;

import com.example.sport_ecommerce.application.model.dto.ConfiguratorDTO;
import com.example.sport_ecommerce.application.model.response.ConfiguratorResponse;
import com.example.sport_ecommerce.application.port.in.configurator.ManageConfiguratorUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/configurator")
@RequiredArgsConstructor
public class ConfiguratorController {
    private final ManageConfiguratorUseCase manageUseCase;


    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<ConfiguratorResponse> create(@RequestBody @Valid ConfiguratorDTO command) {
        ConfiguratorResponse configurator = manageUseCase.create(command);
        return ResponseEntity.status(HttpStatus.CREATED).body(configurator);
    }
}
