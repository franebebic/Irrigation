package com.fb.irrigation.service;

import com.fb.irrigation.dto.ActivityDTO;
import com.fb.irrigation.kafka.event.ValveStatus;
import com.fb.irrigation.mapper.ActivityMapper;
import com.fb.irrigation.model.*;
import com.fb.irrigation.repository.ActivityRepository;
import com.fb.irrigation.specification.ActivitySpecification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class ActivityServiceImpl implements ActivityService {
    private final ActivityRepository activityRepository;
    private final ActivityMapper activityMapper;

    @Override
    public void create(Valve valve, ValveStatus status, ActivityType activityType) {
        ActivityStatus activityStatus=null;
        Duration duration=null;
        Instant startTime=null;
        Instant endTime=null;


        switch (status){
            case OPEN -> {
                activityStatus=ActivityStatus.RUNNING;
                startTime=Instant.now();
            }
            case CLOSED -> {
                activityStatus=ActivityStatus.EXECUTED;
                if(activityRepository.findTopByValveOrderByStartTimeDesc(valve).isPresent()) {
                    Activity previousActivity = activityRepository.findTopByValveOrderByStartTimeDesc(valve).get();
                    if (previousActivity.getStatus() != ActivityStatus.RUNNING)
                        throw new IllegalStateException("Last activity for valve " + valve.getName() + " was not RUNNING but " + previousActivity.getStatus());

                    startTime = previousActivity.getStartTime();
                    endTime = Instant.now();
                    duration = Duration.between(startTime, endTime);
                }
            }
        }

        Plot plot=valve.getPlot();

        Activity activity = Activity.builder()
                .type(activityType)
                .valve(valve)
                .valveIdSnapshot(valve.getId())
                .valveNameSnapshot(valve.getName())
                .plot(plot)
                .plotIdSnapshot(plot.getId())
                .plotNameSnapshot(plot.getName())
                .status(activityStatus)
                .startTime(startTime)
                .endTime(endTime)
                .duration(duration)
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
