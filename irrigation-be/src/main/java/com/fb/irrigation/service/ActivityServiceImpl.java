package com.fb.irrigation.service;

import com.fb.irrigation.dto.ActivityDTO;
import com.fb.irrigation.mapper.ActivityMapper;
import com.fb.irrigation.model.*;
import com.fb.irrigation.repository.ActivityRepository;
import com.fb.irrigation.repository.PlotRepository;
import com.fb.irrigation.repository.ValveRepository;
import com.fb.irrigation.specification.ActivitySpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ActivityServiceImpl implements ActivityService {
    private final ActivityRepository activityRepository;
    private final ActivityMapper activityMapper;
    private final PlotRepository plotRepository;
    private final ValveRepository valveRepository;

    @Override
    public void create(Valve valve, ValveStatus status, Plot plot) {
        Activity activity = Activity.builder()
                .type(ActivityType.MANUAL)
                .valve(valve)
                .valveIdSnapshot(valve.getId())
                .valveNameSnapshot(valve.getName())
                .plot(plot)
                .plotIdSnapshot(plot.getId())
                .plotNameSnapshot(plot.getName())
                .status(ActivityStatus.RUNNING)
                .startTime(LocalDateTime.now())
                .build();
        activityRepository.save(activity);
    }

    @Override
    public Page<ActivityDTO> findAll(int page, int size, String valveName, String plotName) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "startTime"));
        Specification<Activity> spec = Specification
                .where(ActivitySpecification.valveNameLike(valveName))
                .and(ActivitySpecification.plotNameLike(plotName));

        return activityRepository.findAll(spec, pageable).map(activityMapper::toDTO);
    }

    @Override
    public List<String> getValveFilterOptions() {
        return activityRepository.findDistinctValveNames();
    }

    @Override
    public List<String> getPlotFilterOptions() {
        return activityRepository.findDistinctPlotNames();
    }
}
