package com.example.weather;

import java.time.ZonedDateTime;

/**
 * This is a data object that represents a weatherForecast response.
 * @param timestamp
 * @param weatherForecast
 */
public record WeatherData(ZonedDateTime timestamp, String weatherForecast) {
}
