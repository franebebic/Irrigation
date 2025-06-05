package com.fb.irrigation.repository;

import com.fb.irrigation.model.Measurement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MeasurementRepository extends JpaRepository<Measurement, Long>, JpaSpecificationExecutor<Measurement> {
    @Query("SELECT DISTINCT m.sensorNameSnapshot FROM Measurement m WHERE m.sensorNameSnapshot IS NOT NULL")
    List<String> findDistinctSensorNames();

    @Query("SELECT DISTINCT m.plotNameSnapshot FROM Measurement m WHERE m.plotNameSnapshot IS NOT NULL")
    List<String> findDistinctPlotNames();
}
