package org.example.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MutantDetectorTest {

    private MutantDetector detector;

    @BeforeEach
    void setUp() {
        detector = new MutantDetector();
    }

    @Test
    void isMutant_MutantHorizontalAndVertical_ShouldBeTrue() {
        String[] dna = {
                "ATGCGA",
                "CAGTGC",
                "TTATGT",
                "AGAAGG",
                "CCCCTA",
                "TCACTG"
        };
        assertTrue(detector.isMutant(dna));
    }

    @Test
    void isMutant_MutantDiagonalDownAndUp_ShouldBeTrue() {
        String[] dna = {
                "ATGCGA",
                "CAGTGC",
                "TTATGT",
                "AGAAAG",
                "CCCCTA",
                "TCACTG"
        };
        assertTrue(detector.isMutant(dna));
    }

    @Test
    void isMutant_OnlyOneSequenceHorizontal_ShouldBeFalse() {
        String[] dna = {
                "ATGCGA",
                "CAGTTT",
                "TTAGGT",
                "AGACGG",
                "TCGTAT",
                "AAAATC"
        };
        assertFalse(detector.isMutant(dna));
    }

    @Test
    void isMutant_NoMutantSequences_ShouldBeFalse() {
        String[] dna = {
                "ATGC",
                "TAGC",
                "CTAG",
                "GCAT"
        };
        assertFalse(detector.isMutant(dna));
    }

    @Test
    void isMutant_EdgeCaseLargeMatrix() {
        String[] dna = {
                "ATGCGAATGCGA",
                "CAGTGCGTGTGC",
                "TTATGTATATGT",
                "AGAAAAAGAAAA",
                "CCCCTACCCCTA",
                "TCACTGTCACTG",
                "ATGCGAATGCGA",
                "CAGTGCGTGTGC",
                "TTATGTATATGT",
                "AGAAAAAGAAAA",
                "CCCCAAACCCCA",
                "TCACTGTCACTG"
        };
        assertTrue(detector.isMutant(dna));
    }

    @Test
    void isMutant_InvalidSizeNxM_ShouldBeFalse() {
        String[] dna = {
                "ATGC",
                "TAGC",
                "CTAG"
        };
        assertFalse(detector.isMutant(dna));
    }

    @Test
    void isMutant_SizeLessThan4_ShouldBeFalse() {
        String[] dna = {
                "ATG",
                "TAG",
                "CTA"
        };
        assertFalse(detector.isMutant(dna));
    }

    @Test
    void isMutant_WithNullRow_ShouldBeFalse() {
        String[] dna = {
                "ATGCGA",
                null,
                "TTATGT",
                "AGAAGG",
                "CCCCTA",
                "TCACTG"
        };
        assertFalse(detector.isMutant(dna));
    }

    @Test
    void isMutant_DnaIsNull_ShouldBeFalse() {
        assertFalse(detector.isMutant(null));
    }

    @Test
    void isMutant_HorizontalMutantInLastRow_ShouldBeFalse() {
        String[] dna = {
                "GATCGA",
                "CTAGAT",
                "TTCCGT",
                "GGCGCT",
                "CCGTTA",
                "TAAAAT"
        };
        assertFalse(detector.isMutant(dna));
    }

    @Test
    void isMutant_DoubleHorizontalAndDoubleVertical() {
        String[] dna = {
                "AAAAAT",
                "TGCGTA",
                "CGTCGA",
                "GGGGTT",
                "ACGTCA",
                "ACGTCA"
        };
        assertTrue(detector.isMutant(dna));
    }

    @Test
    void isMutant_TwoSequencesInTheSameRow() {
        String[] dna = {
                "AAAATCCCC",
                "TGCGTATAT",
                "CGTCGACGC",
                "GGGGTTTTA",
                "ACGTCAACA",
                "ACGTCAACA",
                "ATGCAGTCG",
                "ATGCAGTCG",
                "ATGCAGTCG"
        };
        assertTrue(detector.isMutant(dna));
    }
}