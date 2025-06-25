package com.fb.irrigation.repository;

import com.fb.irrigation.model.Plantation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PlantationRepository extends JpaRepository<Plantation, Long> {
    boolean existsByCrop_Id(Long id);
    boolean existsByPlot_Id(Long id);
    List<Plantation> findAllByCrop_Id(Long id);
}
