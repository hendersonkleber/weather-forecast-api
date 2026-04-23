package com.hendersonkleber.weatherforecast.controller;


import com.hendersonkleber.weatherforecast.controller.dto.CityForecastResponse;
import com.hendersonkleber.weatherforecast.repository.dto.CityView;
import com.hendersonkleber.weatherforecast.controller.dto.WeatherForecastResponse;
import com.hendersonkleber.weatherforecast.service.WeatherForecastService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/weather-forecast")
public class WeatherForecastController {

    private final WeatherForecastService weatherForecastService;

    public WeatherForecastController(WeatherForecastService weatherForecastService) {
        this.weatherForecastService = weatherForecastService;
    }

    @GetMapping(path = "/{cityId}/forecast")
    public ResponseEntity<WeatherForecastResponse> getForecast(
            @PathVariable Long cityId,
            @RequestParam(defaultValue = "1") Integer days
    ) {
        var response = this.weatherForecastService.getForecast(cityId, days);
        return ResponseEntity.ok(response);
    }

    @GetMapping(path = "/{cityId}/history")
    public ResponseEntity<WeatherForecastResponse> getHistory(
            @PathVariable Long cityId,
            @RequestParam(defaultValue = "7") Integer days
    ) {
        var response = this.weatherForecastService.getHistory(cityId, days);
        return ResponseEntity.ok(response);
    }

    @PostMapping(path = "/favorites")
    public ResponseEntity<List<CityForecastResponse>> getFavorites(
            @RequestBody List<Long> cityIds
    ) {
        var response = this.weatherForecastService.getCities(cityIds);
        return ResponseEntity.ok(response);
    }

    @GetMapping(path = "/popular")
    public ResponseEntity<List<CityForecastResponse>> getPopular() {
        var response = this.weatherForecastService.getPopularCities();
        return ResponseEntity.ok(response);
    }
}
