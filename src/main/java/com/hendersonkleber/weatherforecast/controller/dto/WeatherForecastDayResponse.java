package com.hendersonkleber.weatherforecast.controller.dto;

import com.hendersonkleber.weatherforecast.entity.WeatherCondition;
import com.hendersonkleber.weatherforecast.entity.WeatherForecastDay;
import com.hendersonkleber.weatherforecast.entity.WeatherForecastHour;
import com.hendersonkleber.weatherforecast.entity.WindDirection;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public record WeatherForecastDayResponse(
        LocalDate date,
        WeatherCondition condition,
        Double temperature2mMax,
        Double temperature2mMin,
        Double apparentTemperatureMax,
        Double apparentTemperatureMin,
        Double precipitationSum,
        Integer relativeHumidity2mMin,
        Integer relativeHumidity2mMax,
        Double precipitationProbabilityMax,
        Double precipitationProbabilityMin,
        LocalDateTime sunriseAt,
        LocalDateTime sunsetAt,
        Double windSpeed10mMax,
        WindDirection windDirection10mDominant,
        List<WeatherForecastHourResponse> hours
) {
    public static WeatherForecastDayResponse from(WeatherForecastDay d, List<WeatherForecastHour> hours) {
        return new WeatherForecastDayResponse(
                d.getDate(),
                d.getCondition(),
                d.getTemperature2mMax(),
                d.getTemperature2mMin(),
                d.getApparentTemperatureMax(),
                d.getApparentTemperatureMin(),
                d.getPrecipitationSum(),
                d.getRelativeHumidity2mMin(),
                d.getRelativeHumidity2mMax(),
                d.getPrecipitationProbabilityMax(),
                d.getPrecipitationProbabilityMin(),
                d.getSunriseAt(),
                d.getSunsetAt(),
                d.getWindSpeed10mMax(),
                d.getWindDirection10mDominant(),
                hours.stream().map(WeatherForecastHourResponse::from).toList()
        );
    }
}
