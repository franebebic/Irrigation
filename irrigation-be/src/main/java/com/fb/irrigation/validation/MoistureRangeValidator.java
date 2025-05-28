package com.fb.irrigation.validation;

import com.fb.irrigation.dto.CropDTO;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class MoistureRangeValidator implements ConstraintValidator<ValidMoistureRange, CropDTO> {

    @Override
    public boolean isValid(CropDTO crop, ConstraintValidatorContext constraintValidatorContext) {
        if(crop==null) return true;
        if(crop.getMinMoisture()==null || crop.getMaxMoisture()==null) return true;
        return crop.getMinMoisture()<crop.getMaxMoisture();
     }
}
