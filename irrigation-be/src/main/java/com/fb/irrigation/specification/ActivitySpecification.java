package com.fb.irrigation.specification;

import com.fb.irrigation.model.Activity;
import org.springframework.data.jpa.domain.Specification;

public class ActivitySpecification {
    public static Specification<Activity> plotNameLike(String plotName) {
        return (root, query, cb) -> {
            if (plotName == null || plotName.isBlank()) return null;
            return cb.like(cb.lower(root.get("plotNameSnapshot")), "%" + plotName.toLowerCase() + "%");
        };
    }

    public static Specification<Activity> valveNameLike(String valveName) {
        return (root, query, cb) -> {
            if (valveName == null || valveName.isBlank()) return null;
            return cb.like(cb.lower(root.get("valveNameSnapshot")), "%" + valveName.toLowerCase() + "%");
        };
    }
}
