package com.fb.irrigation.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Constraint(validatedBy = ActivityTimeValidator.class)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ValidActivityTime {
    String message() default "Activity end time must be after activity start time";
    Class <?>[] groups() default{};
    Class <? extends Payload>[] payload() default{};
}
