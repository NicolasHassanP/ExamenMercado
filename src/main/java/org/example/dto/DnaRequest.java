package org.example.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.example.validation.ValidDnaSequence;

@Schema(description = "Request para verificar si un ADN es mutante")
@Data
public class DnaRequest {

    @Schema(
            description = "Secuencia de ADN (matriz NxN) con solo caracteres A, T, C, G.",
            example = "[\"ATGCGA\",\"CAGTGC\",\"TTATGT\",\"AGAAAG\",\"CCCCTA\",\"TCACTG\"]",
            required = true
    )
    @ValidDnaSequence
    private String[] dna;
}