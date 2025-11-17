package org.example.service;

import org.example.entity.DnaRecord;
import org.example.repository.DnaRecordRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class MutantServiceTest {

    private MutantService mutantService;
    private DnaRecordRepository dnaRecordRepository;
    private MutantDetector mutantDetector;

    @BeforeEach
    void setUp() {
        dnaRecordRepository = mock(DnaRecordRepository.class);
        mutantDetector = mock(MutantDetector.class);
        mutantService = new MutantService(dnaRecordRepository, mutantDetector);
    }

    @Test
    void isMutant_WhenCachedMutant_ShouldReturnTrueAndNotDetect() {
        String[] dna = {"ATGCGA", "CAGTGC", "TTATGT", "AGAAGG", "CCCCTA", "TCACTG"};

        DnaRecord cachedRecord = DnaRecord.builder().dnaHash("hash-simulado").isMutant(true).build();

        when(dnaRecordRepository.findByDnaHash(anyString())).thenReturn(Optional.of(cachedRecord));

        boolean result = mutantService.isMutant(dna);

        assertTrue(result);
        verify(dnaRecordRepository, times(1)).findByDnaHash(anyString());
        verify(mutantDetector, never()).isMutant(any());
        verify(dnaRecordRepository, never()).save(any());
    }

    @Test
    void isMutant_WhenCachedHuman_ShouldReturnFalseAndNotDetect() {
        String[] dna = {"ATGC", "TAGC", "CTAG", "GCAT"};

        DnaRecord cachedRecord = DnaRecord.builder().dnaHash("hash-simulado").isMutant(false).build();

        when(dnaRecordRepository.findByDnaHash(anyString())).thenReturn(Optional.of(cachedRecord));

        boolean result = mutantService.isMutant(dna);

        assertFalse(result);
        verify(dnaRecordRepository, times(1)).findByDnaHash(anyString());
        verify(mutantDetector, never()).isMutant(any());
        verify(dnaRecordRepository, never()).save(any());
    }

    @Test
    void isMutant_WhenNewMutant_ShouldReturnTrueAndSave() {
        String[] dna = {"AAAA", "TGCA", "ATTC", "GATA"};

        when(dnaRecordRepository.findByDnaHash(anyString())).thenReturn(Optional.empty());
        when(mutantDetector.isMutant(dna)).thenReturn(true);

        boolean result = mutantService.isMutant(dna);

        assertTrue(result);
        verify(dnaRecordRepository, times(1)).findByDnaHash(anyString());
        verify(mutantDetector, times(1)).isMutant(dna);

        ArgumentCaptor<DnaRecord> captor = ArgumentCaptor.forClass(DnaRecord.class);
        verify(dnaRecordRepository, times(1)).save(captor.capture());
        assertTrue(captor.getValue().isMutant());
    }

    @Test
    void isMutant_WhenNewHuman_ShouldReturnFalseAndSave() {
        String[] dna = {"ATGC", "TAGC", "CTAG", "GCAT"};

        when(dnaRecordRepository.findByDnaHash(anyString())).thenReturn(Optional.empty());
        when(mutantDetector.isMutant(dna)).thenReturn(false);

        boolean result = mutantService.isMutant(dna);

        assertFalse(result);
        verify(dnaRecordRepository, times(1)).findByDnaHash(anyString());
        verify(mutantDetector, times(1)).isMutant(dna);

        ArgumentCaptor<DnaRecord> captor = ArgumentCaptor.forClass(DnaRecord.class);
        verify(dnaRecordRepository, times(1)).save(captor.capture());
        assertFalse(captor.getValue().isMutant());
    }
}