package com.example.sport_ecommerce.infrastructure.adapter.web.controller;

import com.example.sport_ecommerce.application.mapper.PresetConfigurationResponseMapper;
import com.example.sport_ecommerce.application.model.command.CreatePresetConfigurationCommand;
import com.example.sport_ecommerce.application.model.response.PresetConfigurationResponse;
import com.example.sport_ecommerce.application.port.in.configuration.GetPresetConfigurationsUseCase;
import com.example.sport_ecommerce.application.port.in.configuration.ManagePresetConfigurationUseCase;
import com.example.sport_ecommerce.domain.model.PresetConfiguration;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/presets")
@RequiredArgsConstructor
public class PresetConfigurationController {

    private final ManagePresetConfigurationUseCase manageUseCase;
    private final GetPresetConfigurationsUseCase getUseCase;
    private final PresetConfigurationResponseMapper responseMapper;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<PresetConfigurationResponse> create(@RequestBody @Valid CreatePresetConfigurationCommand command) {
        PresetConfiguration created = manageUseCase.create(command);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseMapper.toResponse(created));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<PresetConfigurationResponse> update(@PathVariable UUID id, @RequestBody @Valid CreatePresetConfigurationCommand command) {
        PresetConfiguration updated = manageUseCase.update(id, command);
        return ResponseEntity.ok(responseMapper.toResponse(updated));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        manageUseCase.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<List<PresetConfigurationResponse>> getByProduct(@PathVariable UUID productId) {
        return ResponseEntity.ok(getUseCase.getPresetsByProduct(productId));
    }
}

