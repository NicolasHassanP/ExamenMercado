package org.example.service;

import org.springframework.stereotype.Component;
import java.util.Set;

@Component
public class MutantDetector {

    private static final int SEQUENCE_LENGTH = 4;
    private static final int MIN_MUTANT_SEQUENCES = 2;
    private static final Set<Character> VALID_BASES = Set.of('A', 'T', 'C', 'G');

    public boolean isMutant(String[] dna) {
        if (dna == null || dna.length < SEQUENCE_LENGTH) {
            return false;
        }

        final int N = dna.length;
        char[][] matrix = new char[N][];
        int mutantSequencesFound = 0;

        for (int i = 0; i < N; i++) {
            String row = dna[i];
            if (row == null || row.length() != N) {
                return false;
            }

            for (char c : row.toCharArray()) {
                if (!VALID_BASES.contains(c)) {
                    return false;
                }
            }
            matrix[i] = row.toCharArray();
        }


        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {


                if (j <= N - SEQUENCE_LENGTH) {
                    if (checkHorizontal(matrix, i, j)) {
                        mutantSequencesFound++;
                        if (mutantSequencesFound >= MIN_MUTANT_SEQUENCES) return true;
                    }
                }


                if (i <= N - SEQUENCE_LENGTH) {
                    if (checkVertical(matrix, i, j)) {
                        mutantSequencesFound++;
                        if (mutantSequencesFound >= MIN_MUTANT_SEQUENCES) return true;
                    }
                }

                if (i <= N - SEQUENCE_LENGTH && j <= N - SEQUENCE_LENGTH) {
                    if (checkDiagonalDown(matrix, i, j)) {
                        mutantSequencesFound++;
                        if (mutantSequencesFound >= MIN_MUTANT_SEQUENCES) return true;
                    }
                }


                if (i >= SEQUENCE_LENGTH - 1 && j <= N - SEQUENCE_LENGTH) {
                    if (checkDiagonalUp(matrix, i, j)) {
                        mutantSequencesFound++;
                        if (mutantSequencesFound >= MIN_MUTANT_SEQUENCES) return true;
                    }
                }
            }
        }

        return false;
    }

    private boolean checkHorizontal(char[][] matrix, int row, int col) {
        final char base = matrix[row][col];
        return base == matrix[row][col + 1] &&
                base == matrix[row][col + 2] &&
                base == matrix[row][col + 3];
    }

    private boolean checkVertical(char[][] matrix, int row, int col) {
        final char base = matrix[row][col];
        return base == matrix[row + 1][col] &&
                base == matrix[row + 2][col] &&
                base == matrix[row + 3][col];
    }

    private boolean checkDiagonalDown(char[][] matrix, int row, int col) {
        final char base = matrix[row][col];
        return base == matrix[row + 1][col + 1] &&
                base == matrix[row + 2][col + 2] &&
                base == matrix[row + 3][col + 3];
    }


    private boolean checkDiagonalUp(char[][] matrix, int row, int col) {
        final char base = matrix[row][col];
        return base == matrix[row - 1][col + 1] &&
                base == matrix[row - 2][col + 2] &&
                base == matrix[row - 3][col + 3];
    }
}