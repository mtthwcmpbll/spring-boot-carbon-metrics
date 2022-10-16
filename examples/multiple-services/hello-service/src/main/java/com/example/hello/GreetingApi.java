package com.example.hello;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GreetingApi {

    ForecastService forecastService;

    public GreetingApi(ForecastService forecastService) {
        this.forecastService = forecastService;
    }

    @GetMapping("/greeting/{name}")
    public String sayHello(@PathVariable String name) {
        String forecast = forecastService.getWeather();
        return String.format("Hello, %s!  It looks like the weather will be %s", name, forecast);
    }

}
