package org.example.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.dto.DnaRequest;
import org.example.dto.StatsResponse;
import org.example.service.MutantService;
import org.example.service.StatsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Tag(name = "Mutant Detector", description = "API para la detección de mutantes mediante análisis de ADN")
public class MutantController {

    private final MutantService mutantService;
    private final StatsService statsService;

    @Operation(summary = "Verificar si un ADN es mutante",
            description = "Recibe una secuencia de ADN y retorna 200 si es mutante o 403 si es humano.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Es mutante"),
                    @ApiResponse(responseCode = "403", description = "No es mutante (humano)"),
                    @ApiResponse(responseCode = "400", description = "ADN inválido (no NxN, caracteres no permitidos, etc.)")
            })
    @PostMapping("/mutant")
    public ResponseEntity<Void> isMutant(@Valid @RequestBody DnaRequest request) {
        if (mutantService.isMutant(request.getDna())) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @Operation(summary = "Obtener estadísticas de análisis de ADN",
            description = "Retorna el conteo de ADN mutante y humano, y su ratio.")
    @GetMapping("/stats")
    public ResponseEntity<StatsResponse> getStats() {
        StatsResponse stats = statsService.getStats();
        return ResponseEntity.ok(stats);
    }
}