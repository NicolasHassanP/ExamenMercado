package org.example.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class StatsResponse {
    @JsonProperty("count_mutant_dna")
    long countMutantDna;
    @JsonProperty("count_human_dna")
    long countHumanDna;
    double ratio;
}