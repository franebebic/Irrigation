package com.fb.irrigation.repository;

import com.fb.irrigation.model.Plot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PlotRepository  extends JpaRepository<Plot, Long> {
    @Query("SELECT DISTINCT p FROM Plot p LEFT JOIN FETCH p.plantations pl LEFT JOIN FETCH p.valve v LEFT JOIN FETCH pl.crop")
    List<Plot> findAllWithPlantationsAndValve();
}
