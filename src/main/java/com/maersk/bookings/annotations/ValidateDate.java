package com.maersk.bookings.annotations;

import com.maersk.bookings.validators.DateValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.METHOD, ElementType.FIELD, ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = DateValidator.class)
public @interface ValidateDate {

    String message() default "Invalid date!";
    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
    boolean optional() default false;

}
