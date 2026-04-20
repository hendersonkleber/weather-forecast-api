package com.hendersonkleber.weatherforecast.client;

import com.hendersonkleber.weatherforecast.config.FeignConfig;
import org.springframework.cloud.openfeign.CollectionFormat;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.util.List;

import com.hendersonkleber.weatherforecast.client.dto.OpenMeteoWeatherForecastResponse;

@FeignClient(
        name = "OpenMeteoClient",
        url = "https://api.open-meteo.com",
        configuration = FeignConfig.class
)
public interface OpenMeteoClient {
    @GetMapping(path = "/v1/forecast")
    OpenMeteoWeatherForecastResponse getWeatherForecastForCity(
            @RequestParam(name = "latitude") String latitude,
            @RequestParam(name = "longitude") String longitude,
            @RequestParam(name = "daily") String daily,
            @RequestParam(name = "hourly") String hourly,
            @RequestParam(name = "timezone", defaultValue = "America/Sao_Paulo") String timezone,
            @RequestParam(name = "forecast_days", defaultValue = "1") Integer forecastDays,
            @RequestParam(name = "past_days", defaultValue = "0") Integer pastDays
    );

    @GetMapping(path = "/v1/forecast")
    @CollectionFormat(feign.CollectionFormat.CSV)
    List<OpenMeteoWeatherForecastResponse> getWeatherForecastForCities(
            @RequestParam(name = "latitude") List<String> latitude,
            @RequestParam(name = "longitude") List<String> longitude,
            @RequestParam(name = "daily") String daily,
            @RequestParam(name = "hourly") String hourly,
            @RequestParam(name = "timezone", defaultValue = "America/Sao_Paulo") String timezone,
            @RequestParam(name = "forecast_days", defaultValue = "1") Integer forecastDays,
            @RequestParam(name = "past_days", defaultValue = "0") Integer pastDays
    );
}
