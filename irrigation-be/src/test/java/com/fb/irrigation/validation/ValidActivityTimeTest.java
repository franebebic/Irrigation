package com.fb.irrigation.validation;

import com.fb.irrigation.model.Activity;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.util.Assert;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertFalse;

public class ValidActivityTimeTest {
    private Validator validator;

    @BeforeEach
    void setup() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void shouldFailWhenStartTimeAfterEndTime(){
        Activity activity=Activity.builder()
                .startTime(LocalDateTime.of(2025, 05, 22, 15, 40))
                .endTime(LocalDateTime.of(2025, 05, 22, 10, 0))
                .plotNameSnapshot("Test plot name")
                .plotIdSnapshot(1L)
                .valveNameSnapshot("Test valve name")
                .valveIdSnapshot(10L)
                .build();

        Set<ConstraintViolation<Activity>> validation = validator.validate(activity);
        assertFalse(validation.isEmpty());
    }
}
