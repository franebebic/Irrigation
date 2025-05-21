package com.fb.irrigation.validation;

import com.fb.irrigation.model.Crop;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class MoistureRangeValidator implements ConstraintValidator<ValidMoistureRange, Crop> {

    @Override
    public boolean isValid(Crop crop, ConstraintValidatorContext constraintValidatorContext) {
        if(crop==null) return true;
        if(crop.getMinMoisture()==null || crop.getMaxMoisture()==null) return true;
        return crop.getMinMoisture()<crop.getMaxMoisture();
     }
}
