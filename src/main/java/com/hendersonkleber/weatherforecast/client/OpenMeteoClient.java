package com.hendersonkleber.weatherforecast.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.util.List;

import com.hendersonkleber.weatherforecast.client.dto.OpenMeteoWeatherForecastResponse;

@FeignClient(
        name = "OpenMeteoClient",
        url = "https://api.open-meteo.com"
)
public interface OpenMeteoClient {
    @GetMapping(path = "/v1/forecast")
    List<OpenMeteoWeatherForecastResponse> getWeatherForecast(
            @RequestParam(name = "latitude") List<BigDecimal> latitudes,
            @RequestParam(name = "longitude") List<BigDecimal> longitudes,
            @RequestParam(name = "daily") String daily,
            @RequestParam(name = "hourly") String hourly,
            @RequestParam(name = "timezone", defaultValue = "America%2FSao_Paulo") String timezone,
            @RequestParam(name = "forecast_days", defaultValue = "1") Integer forecastDays
    );
}
