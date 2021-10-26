package com.example.demopractice.common.validations;

import org.apache.commons.validator.GenericValidator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class DateFormatValidation implements ConstraintValidator<DateValidator, String> {

    private String pattern;

    @Override
    public void initialize(DateValidator constraintAnnotation) {
        this.pattern = constraintAnnotation.pattern();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintContext) {
        if (value == null) {
            return true;
        }

        try {
            return GenericValidator.isDate(value, pattern, true);
        } catch (Exception e) {
            return false;
        }
    }
}