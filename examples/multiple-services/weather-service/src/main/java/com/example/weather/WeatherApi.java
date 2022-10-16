package com.example.weather;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * This is a simple REST endpoint that calls the an external Weather API, transforms the response, and returns it.
 * It's not particularly useful, but it lets us experiment with calling another service that depends on an external
 * interface that we don't control (and can't instrument).
 */
@RestController()
public class WeatherApi {

    NationalWeatherService weatherService;

    WeatherApi(NationalWeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @GetMapping(value = "/weather", produces = MediaType.APPLICATION_JSON_VALUE)
    public WeatherData getWeather() {
        return weatherService.getWeather();
    }

}

