package com.hendersonkleber.weatherforecast.controller.dto;

import com.hendersonkleber.weatherforecast.entity.WeatherCondition;
import com.hendersonkleber.weatherforecast.entity.WeatherForecastHour;
import com.hendersonkleber.weatherforecast.entity.WindDirection;

import java.time.LocalTime;

public record WeatherForecastHourResponse(
        LocalTime time,
        WeatherCondition condition,
        Integer cloudCover,
        Double temperature2m,
        Double apparentTemperature,
        Double precipitation,
        Double precipitationProbability,
        Integer relativeHumidity2m,
        WindDirection windDirection10m,
        Double windSpeed10m
) {
    public static WeatherForecastHourResponse from(WeatherForecastHour h) {
        return new WeatherForecastHourResponse(
                h.getHour(),
                h.getCondition(),
                h.getCloudCover(),
                h.getTemperature2m(),
                h.getApparentTemperature(),
                h.getPrecipitation(),
                h.getPrecipitationProbability(),
                h.getRelativeHumidity2m(),
                h.getWindDirection10m(),
                h.getWindSpeed10m()
        );
    }
}
