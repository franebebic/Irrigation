package com.fb.irrigation.repository;

import com.fb.irrigation.model.Measurement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MeasurementRepository extends JpaRepository<Measurement, Long>{

}
