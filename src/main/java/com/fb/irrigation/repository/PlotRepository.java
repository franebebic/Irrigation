package com.fb.irrigation.repository;

import com.fb.irrigation.model.Plot;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlotRepository  extends JpaRepository<Plot, Long> {
}
