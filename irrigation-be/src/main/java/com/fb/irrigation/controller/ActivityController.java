package com.fb.irrigation.controller;

import com.fb.irrigation.dto.ActivityDTO;
import com.fb.irrigation.service.ActivityService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/activities")
@RequiredArgsConstructor
public class ActivityController {
    private final ActivityService activityService;

    @GetMapping
    public ResponseEntity<Page<ActivityDTO>> getActivites(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "50") int size,
            @RequestParam(required = false) String valveName,
            @RequestParam(required = false) String plotName) {
        Page<ActivityDTO> dtoPage = activityService.findAll(page, size, valveName, plotName);
        return ResponseEntity.ok(dtoPage);
    }

    @GetMapping("/valve-options")
    public List<String> getValveFilterOptions() {
        return activityService.getValveFilterOptions();
    }

    @GetMapping("/plot-options")
    public List<String> getPlotFilterOptions() {
        return activityService.getPlotFilterOptions();
    }
}
