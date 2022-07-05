package com.maersk.bookings.validators;

import com.datastax.oss.driver.shaded.guava.common.base.Strings;
import com.maersk.bookings.annotations.ValidateDate;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DateValidator implements ConstraintValidator<ValidateDate, String> {

    private Boolean isOptional;

    @Override
    public void initialize(ValidateDate validDate) {
        this.isOptional = validDate.optional();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        boolean validDate = isValid( value);
        return isOptional ? (validDate || (Strings.isNullOrEmpty(value))) : validDate;
    }

    private static boolean isValid(String value) {
        try {
            DateTimeFormatter.ISO_INSTANT.parse(value);
        } catch (DateTimeParseException e) {
            return false;
        }
        return true;
    }
}
