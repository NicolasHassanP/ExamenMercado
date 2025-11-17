package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.dto.StatsResponse;
import org.example.repository.DnaRecordRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StatsService {

    private final DnaRecordRepository dnaRecordRepository;

    public StatsResponse getStats() {
        long mutantCount = dnaRecordRepository.countByIsMutant(true);
        long humanCount = dnaRecordRepository.countByIsMutant(false);

        double ratio = 0.0;
        if (humanCount == 0) {
            // Caso especial: Si no hay humanos, el ratio es el número de mutantes si existe alguno.
            ratio = mutantCount > 0 ? (double) mutantCount : 0.0;
        } else {
            ratio = (double) mutantCount / humanCount;
        }

        return StatsResponse.builder()
                .countMutantDna(mutantCount)
                .countHumanDna(humanCount)
                .ratio(ratio)
                .build();
    }
}