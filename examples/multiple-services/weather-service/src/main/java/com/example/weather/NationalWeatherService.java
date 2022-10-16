package com.example.weather;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import io.micrometer.core.instrument.MeterRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import java.time.ZonedDateTime;

@Service
public class NationalWeatherService {

    private static final Logger logger = LoggerFactory.getLogger(NationalWeatherService.class);
    private RestTemplate restTemplate;
    private String baseUrl;

    public NationalWeatherService(
            RestTemplate restTemplate,
            @Value("${nationalWeatherService.baseUrl}") String baseUrl) {
        this.restTemplate = restTemplate;
        this.baseUrl = baseUrl;
    }

    WeatherData getWeather() {
        // hard-coded for now - there aren't any parameters to change the actual outgoing call
        String url = String.format("%s/gridpoints/TOP/31,80/forecast", baseUrl);

        try {
            String weatherJson = restTemplate.getForObject(url, String.class);
            JsonObject jobj = new Gson().fromJson(weatherJson, JsonObject.class);

            JsonObject properties = jobj.getAsJsonObject("properties");
            JsonObject firstPeriod = properties.getAsJsonArray("periods").get(0).getAsJsonObject();
            String forecast = firstPeriod.get("shortForecast").getAsString();

            return new WeatherData(ZonedDateTime.now(), forecast);
        } catch (HttpStatusCodeException exc) {
            logger.error("Received a non-200 HTTP status!  " + exc.getStatusCode());
            throw exc;
        } catch (RestClientException exc) {
            logger.error("There was a problem connecting to the Weather API!");
            throw exc; // rethrow for now, but we probably want to handle this exception state gracefully
        }
    }

}
