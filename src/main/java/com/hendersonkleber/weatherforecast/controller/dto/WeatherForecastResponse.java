package com.hendersonkleber.weatherforecast.controller.dto;

import java.util.List;

public record WeatherForecastResponse(
        CityResponse city,
        List<WeatherForecastDayResponse> days
) {
}
