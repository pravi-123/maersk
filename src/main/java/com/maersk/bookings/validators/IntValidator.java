package com.maersk.bookings.validators;

import com.maersk.bookings.annotations.ValidateInteger;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.ArrayList;
import java.util.List;

public class IntValidator implements ConstraintValidator<ValidateInteger, Integer> {
    private List<Integer> valueList;

    @Override
    public void initialize(ValidateInteger constraintAnnotation) {
        valueList = new ArrayList<>();
        for(int val : constraintAnnotation.acceptedValues()) {
            valueList.add(val);
        }
    }

    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext context) {
        return valueList.contains(value);
    }
}
