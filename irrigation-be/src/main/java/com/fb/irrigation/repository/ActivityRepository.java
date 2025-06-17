package com.fb.irrigation.repository;

import com.fb.irrigation.model.Activity;
import com.fb.irrigation.model.Valve;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ActivityRepository extends JpaRepository<Activity, Long>, JpaSpecificationExecutor<Activity> {
    @Query("SELECT DISTINCT a.valveNameSnapshot FROM Activity a WHERE a.valveNameSnapshot IS NOT NULL")
    List<String> findDistinctValveNames();

    @Query("SELECT DISTINCT a.plotNameSnapshot FROM Activity a WHERE a.plotNameSnapshot IS NOT NULL")
    List<String> findDistinctPlotNames();

    Optional<Activity> findTopByValveOrderByStartTimeDesc(Valve valve);
}
