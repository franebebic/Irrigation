package com.fb.irrigation.validation;

import com.fb.irrigation.model.Activity;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ActivityTimeValidator implements ConstraintValidator<ValidActivityTime, Activity> {

    @Override
    public boolean isValid(Activity activity, ConstraintValidatorContext constraintValidatorContext) {
        if(activity==null) return true;
        if(activity.getStartTime()==null || activity.getEndTime()==null) return true;
        return activity.getStartTime().isBefore(activity.getEndTime());
     }
}
