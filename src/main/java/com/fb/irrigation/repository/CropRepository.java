package com.fb.irrigation.repository;

import com.fb.irrigation.model.Crop;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CropRepository extends JpaRepository<Crop, Long> {
}
