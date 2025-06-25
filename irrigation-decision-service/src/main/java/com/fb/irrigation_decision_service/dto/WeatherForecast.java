package com.fb.irrigation_decision_service.dto;

import lombok.Data;

import java.util.List;

@Data
public class WeatherForecast {
    private List<String> time;
    private List<Double> precipitation;

    public double getTotalPrecipitationNextHours(int hours) {
        if (precipitation == null || precipitation.isEmpty()) return 0.0;
        return precipitation.stream()
                .limit(hours)
                .mapToDouble(Double::doubleValue)
                .sum();
    }
}
