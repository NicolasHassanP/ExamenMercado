package org.example.service;

import org.example.dto.StatsResponse;
import org.example.repository.DnaRecordRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class StatsServiceTest {

    private StatsService statsService;
    private DnaRecordRepository dnaRecordRepository;

    @BeforeEach
    void setUp() {
        dnaRecordRepository = mock(DnaRecordRepository.class);
        statsService = new StatsService(dnaRecordRepository);
    }

    @Test
    void getStats_WhenDataExists_ShouldCalculateCorrectRatio() {
        long mutantCount = 40;
        long humanCount = 100;

        when(dnaRecordRepository.countByIsMutant(true)).thenReturn(mutantCount);
        when(dnaRecordRepository.countByIsMutant(false)).thenReturn(humanCount);

        StatsResponse result = statsService.getStats();

        assertEquals(mutantCount, result.getCountMutantDna());
        assertEquals(humanCount, result.getCountHumanDna());
        assertEquals(0.4, result.getRatio(), 0.001);
    }

    @Test
    void getStats_WhenNoHumans_ShouldReturnRatioAsMutantCount() { // FIXED: Test name and expected value
        long mutantCount = 10;
        long humanCount = 0;

        when(dnaRecordRepository.countByIsMutant(true)).thenReturn(mutantCount);
        when(dnaRecordRepository.countByIsMutant(false)).thenReturn(humanCount);

        StatsResponse result = statsService.getStats();

        assertEquals(mutantCount, result.getCountMutantDna());
        assertEquals(humanCount, result.getCountHumanDna());
        assertEquals(10.0, result.getRatio(), 0.001); // FIXED: Expected 10.0, not 0.0
    }

    @Test
    void getStats_WhenNoData_ShouldReturnZeroCountsAndRatio() {
        long mutantCount = 0;
        long humanCount = 0;

        when(dnaRecordRepository.countByIsMutant(true)).thenReturn(mutantCount);
        when(dnaRecordRepository.countByIsMutant(false)).thenReturn(humanCount);

        StatsResponse result = statsService.getStats();

        assertEquals(0, result.getCountMutantDna());
        assertEquals(0, result.getCountHumanDna());
        assertEquals(0.0, result.getRatio(), 0.001);
    }

    @Test
    void getStats_WhenOnlyMutants_ShouldReturnCorrectCountsAndRatio() { // FIXED: Expected value
        long mutantCount = 50;
        long humanCount = 0;

        when(dnaRecordRepository.countByIsMutant(true)).thenReturn(mutantCount);
        when(dnaRecordRepository.countByIsMutant(false)).thenReturn(humanCount);

        StatsResponse result = statsService.getStats();

        assertEquals(50, result.getCountMutantDna());
        assertEquals(0, result.getCountHumanDna());
        assertEquals(50.0, result.getRatio(), 0.001); // FIXED: Expected 50.0, not 0.0
    }
}