package com.fb.irrigation.repository;

import com.fb.irrigation.model.Valve;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ValveRepository extends JpaRepository<Valve, Long> {
    Optional<Valve> findByPlotId(Long plotId);
}
