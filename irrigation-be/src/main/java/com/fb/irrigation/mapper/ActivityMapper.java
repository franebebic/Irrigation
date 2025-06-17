package com.fb.irrigation.mapper;

import com.fb.irrigation.dto.ActivityDTO;
import com.fb.irrigation.model.Activity;
import com.fb.irrigation.model.Plot;
import com.fb.irrigation.model.Valve;
import org.springframework.stereotype.Component;

@Component
public class ActivityMapper {
    public ActivityDTO toDTO(Activity activity) {
        Plot plot = activity.getPlot();
        Valve valve = activity.getValve();

        return ActivityDTO.builder()
                .id(activity.getId())
                .type(activity.getType())
                .plotId(plot != null ? plot.getId() : null)
                .plotName(plot != null ? plot.getName() : null)
                .plotIdSnapshot(activity.getPlotIdSnapshot())
                .plotNameSnapshot(activity.getPlotNameSnapshot())
                .valveId(valve != null ? valve.getId() : null)
                .valveName(valve != null ? valve.getName() : null)
                .valveIdSnapshot(activity.getValveIdSnapshot())
                .valveNameSnapshot(activity.getValveNameSnapshot())
                .status(activity.getStatus())
                .startTime(activity.getStartTime())
                .endTime(activity.getEndTime())
                .duration(activity.getDuration())
                .build();
    }

    public Activity toEntity(ActivityDTO dto, Plot plot, Valve valve) {
        return Activity.builder()
                .id(dto.getId())
                .type(dto.getType())
                .plot(plot)
                .plotIdSnapshot(dto.getPlotIdSnapshot())
                .plotNameSnapshot(dto.getPlotNameSnapshot())
                .valve(valve)
                .valveIdSnapshot(dto.getValveIdSnapshot())
                .valveNameSnapshot(dto.getValveNameSnapshot())
                .status(dto.getStatus())
                .startTime(dto.getStartTime())
                .endTime(dto.getEndTime())
                .duration(dto.getDuration())
                .build();
    }
}
