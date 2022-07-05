package com.maersk.bookings.annotations;

import com.maersk.bookings.validators.IntValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.METHOD, ElementType.FIELD, ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = IntValidator.class)
@Documented
public @interface ValidateInteger {

    int[] acceptedValues();
    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
    String message() default "Invalid value";

}
