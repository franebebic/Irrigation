package com.fb.irrigation_decision_service.dto;

import lombok.Data;

@Data
public class OpenMeteoResponse {
    private WeatherForecast hourly;

}
