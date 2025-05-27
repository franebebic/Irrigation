package com.fb.irrigation.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Constraint(validatedBy = MoistureRangeValidator.class)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ValidMoistureRange {
    String message() default "Minimum moisture must be less than maximum moisture";
    Class <?>[] groups() default{};
    Class <? extends Payload>[] payload() default{};
}
