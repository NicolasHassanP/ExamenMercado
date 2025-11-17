package org.example.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.stereotype.Component;

@Component
public class ValidDnaSequenceValidator implements ConstraintValidator<ValidDnaSequence, String[]> {

    private static final String VALID_CHARS_REGEX = "[ATCG]+";
    private static final int MIN_SIZE = 4;

    @Override
    public boolean isValid(String[] dna, ConstraintValidatorContext context) {
        if (dna == null || dna.length < MIN_SIZE) {
            return false;
        }

        int N = dna.length;
        for (String row : dna) {
            if (row == null || row.length() != N) {
                return false;
            }
            if (!row.matches(VALID_CHARS_REGEX)) {
                return false;
            }
        }

        return true;
    }
}