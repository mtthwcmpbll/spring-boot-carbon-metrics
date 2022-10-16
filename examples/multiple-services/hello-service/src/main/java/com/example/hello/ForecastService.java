package com.example.hello;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * Periodically calls the weather service API and spits the data out to logs
 */
@Service
public class ForecastService {

    private static final Logger logger = LoggerFactory.getLogger(ForecastService.class);
    private RestTemplate restTemplate;
    private String baseUrl;

    public ForecastService(
            RestTemplate restTemplate,
            @Value("${weatherServiceForecast.baseUrl}") String baseUrl) {
        this.restTemplate = restTemplate;
        this.baseUrl = baseUrl;
    }

    public String getWeather() {
        String url = String.format("%s/weather", baseUrl);

        String weatherJson = restTemplate.getForObject(url, String.class);
        JsonObject jobj = new Gson().fromJson(weatherJson, JsonObject.class);
        String forecast = jobj.get("weatherForecast").getAsString();

        return  forecast;
    }

}
