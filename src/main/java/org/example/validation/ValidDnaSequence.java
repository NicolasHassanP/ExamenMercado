package org.example.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = ValidDnaSequenceValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidDnaSequence {
    String message() default "DNA sequence array must be square (NxN), minimum size 4, and contain only A, T, C, G characters.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}