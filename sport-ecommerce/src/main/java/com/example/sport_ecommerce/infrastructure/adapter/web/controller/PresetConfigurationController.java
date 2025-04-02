package com.example.sport_ecommerce.infrastructure.adapter.web.controller;

import com.example.sport_ecommerce.application.mapper.PresetConfigurationResponseMapper;
import com.example.sport_ecommerce.application.model.command.CreatePresetConfigurationCommand;
import com.example.sport_ecommerce.application.model.response.PresetCatalogResponse;
import com.example.sport_ecommerce.application.model.response.PresetConfigurationResponse;
import com.example.sport_ecommerce.application.port.in.configuration.GetPresetConfigurationsUseCase;
import com.example.sport_ecommerce.application.port.in.configuration.ManagePresetConfigurationUseCase;
import com.example.sport_ecommerce.domain.model.PresetConfiguration;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
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
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<PresetConfigurationResponse> create(@RequestBody @Valid CreatePresetConfigurationCommand command) {
        PresetConfiguration created = manageUseCase.create(command);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseMapper.toConfigurationResponse(created));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<PresetConfigurationResponse> update(@PathVariable UUID id, @RequestBody @Valid CreatePresetConfigurationCommand command) {
        PresetConfiguration updated = manageUseCase.update(id, command);
        return ResponseEntity.ok(responseMapper.toConfigurationResponse(updated));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        manageUseCase.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<List<PresetConfigurationResponse>> getByProduct(@PathVariable UUID productId) {
        return ResponseEntity.ok(getUseCase.getPresetsByProduct(productId));
    }

    @GetMapping("/catalog")
    public ResponseEntity<List<PresetCatalogResponse>> getAllPresetsWithProductAndCategory() {
        return ResponseEntity.ok(getUseCase.getAllPresets());
    }
}
