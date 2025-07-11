package com.fb.irrigation.service;

import com.fb.irrigation.dto.ActivityDTO;
import com.fb.irrigation.model.ActivityType;
import com.fb.irrigation.model.Valve;
import com.fb.irrigation.kafka.event.ValveStatus;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ActivityService {
    Page<ActivityDTO> findAll(int page, int size, String valveName, String plotName);

    List<String> getValveFilterOptions();

    List<String> getPlotFilterOptions();

    void create(Valve valve, ValveStatus status, ActivityType activityType);
}
