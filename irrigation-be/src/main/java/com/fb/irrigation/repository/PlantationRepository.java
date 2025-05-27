package com.fb.irrigation.repository;

import com.fb.irrigation.model.Plantation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlantationRepository extends JpaRepository<Plantation, Long> {
}
