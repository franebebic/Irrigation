package com.fb.irrigation_decision_service.service.weather;

import com.fb.irrigation_decision_service.dto.OpenMeteoResponse;
import com.fb.irrigation_decision_service.dto.WeatherForecast;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Map;

@Service
public class WeatherService {

    private final RestTemplate restTemplate = new RestTemplate();

    public WeatherForecast getHourlyPrecipitation(double latitude, double longitude) {
        String url = UriComponentsBuilder
                .fromUriString("https://api.open-meteo.com/v1/forecast")
                .queryParam("latitude", latitude)
                .queryParam("longitude", longitude)
                .queryParam("hourly", "precipitation")
                .build()
                .toUriString();

        OpenMeteoResponse response = restTemplate.getForObject(url, OpenMeteoResponse.class);
        return response != null ? response.getHourly() : null;
    }
}