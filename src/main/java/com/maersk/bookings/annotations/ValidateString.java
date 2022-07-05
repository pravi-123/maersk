package com.maersk.bookings.annotations;

import com.maersk.bookings.validators.StringValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.METHOD, ElementType.FIELD, ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = StringValidator.class)
public @interface ValidateString {

    String[] acceptedValues();
    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
    String message() default "Invalid value";

}
