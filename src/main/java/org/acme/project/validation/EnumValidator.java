package org.acme.project.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public class EnumValidator implements ConstraintValidator<ValidateEnum, Object> {

    private Class<? extends Enum<?>> enumClass;
    private boolean ignoreCase;
    private Set<String> acceptedValues;

    @Override
    public void initialize(ValidateEnum constraintAnnotation) {
        this.enumClass = constraintAnnotation.enumClass();
        this.ignoreCase = constraintAnnotation.ignoreCase();
        this.acceptedValues = Arrays.stream(enumClass.getEnumConstants())
                .map(Enum::name)
                .collect(Collectors.toSet());
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }

        if (value instanceof Enum<?> enumValue) {
            return enumClass.isInstance(enumValue);
        }

        if (value instanceof CharSequence textValue) {
            String candidate = textValue.toString().trim();
            if (candidate.isEmpty()) {
                return true;
            }

            if (ignoreCase) {
                return acceptedValues.stream().anyMatch(valid -> valid.equalsIgnoreCase(candidate));
            }

            return acceptedValues.contains(candidate);
        }

        return false;
    }
}

