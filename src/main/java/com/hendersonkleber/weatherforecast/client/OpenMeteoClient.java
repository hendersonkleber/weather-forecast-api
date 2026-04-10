package com.hendersonkleber.weatherforecast.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;

import com.hendersonkleber.weatherforecast.client.dto.OpenMeteoWeatherForecastResponse;

@FeignClient(
        name = "OpenMeteoClient",
        url = "https://api.open-meteo.com/v1"
)
public interface OpenMeteoClient {
    @GetMapping(path = "/forecast")
    OpenMeteoWeatherForecastResponse getWeatherForecast(
            @RequestParam(name = "latitude") BigDecimal latitude,
            @RequestParam(name = "longitude") BigDecimal longitude,
            @RequestParam(name = "daily") String daily,
            @RequestParam(name = "hourly") String hourly,
            @RequestParam(name = "timezone", defaultValue = "America%2FSao_Paulo") String timezone,
            @RequestParam(name = "forecast_days", defaultValue = "1") Integer forecastDays
    );
}
