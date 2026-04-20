package com.hendersonkleber.weatherforecast.service;

import com.hendersonkleber.weatherforecast.client.OpenMeteoClient;
import com.hendersonkleber.weatherforecast.client.dto.OpenMeteoWeatherForecastResponse;
import com.hendersonkleber.weatherforecast.entity.*;
import com.hendersonkleber.weatherforecast.exception.ExternalApiException;
import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class WeatherForecastSyncService {
    private final Logger log = LoggerFactory.getLogger(WeatherForecastSyncService.class);

    private final OpenMeteoClient openMeteoClient;
    private final WeatherForecastPersistenceService weatherForecastPersistenceService;

    public WeatherForecastSyncService(OpenMeteoClient openMeteoClient, WeatherForecastPersistenceService weatherForecastPersistenceService) {
        this.openMeteoClient = openMeteoClient;
        this.weatherForecastPersistenceService = weatherForecastPersistenceService;
    }

    @Retry(name = "weatherForecastSyncRetry")
    @CircuitBreaker(name = "weatherForecastSyncCircuitBreaker", fallbackMethod = "fallback")
    @RateLimiter(name = "weatherForecastSyncRateLimiter")
    @Bulkhead(name = "weatherForecastSyncBulkhead")
    public void sync(List<City> cities) {
        List<String> latitudes = cities.stream().map(City::getLatitude).map(BigDecimal::toString).toList();
        List<String> longitudes = cities.stream().map(City::getLongitude).map(BigDecimal::toString).toList();

        List<OpenMeteoWeatherForecastResponse> response = this.openMeteoClient.getWeatherForecastForCities(
                latitudes,
                longitudes,
                "weather_code,temperature_2m_max,temperature_2m_min,apparent_temperature_max,apparent_temperature_min,sunrise,sunset,precipitation_sum,relative_humidity_2m_min,relative_humidity_2m_max,precipitation_probability_max,precipitation_probability_min,wind_speed_10m_max,wind_direction_10m_dominant",
                "temperature_2m,relative_humidity_2m,apparent_temperature,precipitation_probability,precipitation,weather_code,cloud_cover,wind_speed_10m,wind_direction_10m",
                "America/Sao_Paulo",
                14,
                7
        );

        if (response.isEmpty()) {
            this.log.info("No weather forecast found");
            return;
        }

        this.weatherForecastPersistenceService.persist(cities, response);
    }

    public void fallback(List<City> cities, Throwable ex) {
        this.log.error("Open Meteo Forecast API Error", ex);
        throw new ExternalApiException("Open Meteo API", "Forecast API unavailable", ex);
    }
}
