package com.fb.irrigation.specification;

import com.fb.irrigation.model.Measurement;
import org.springframework.data.jpa.domain.Specification;

public class MeasurementSpecification {
    public static Specification<Measurement> sensorNameLike(String sensorName) {
        return (root, query, cb) -> {
            if (sensorName == null || sensorName.isBlank()) return null;
            return cb.like(cb.lower(root.get("sensorNameSnapshot")), "%" + sensorName.toLowerCase() + "%");
        };
    }

    public static Specification<Measurement> plotNameLike(String plotName) {
        return (root, query, cb) -> {
            if (plotName == null || plotName.isBlank()) return null;
            return cb.like(cb.lower(root.get("plotNameSnapshot")), "%" + plotName.toLowerCase() + "%");
        };
    }
}
