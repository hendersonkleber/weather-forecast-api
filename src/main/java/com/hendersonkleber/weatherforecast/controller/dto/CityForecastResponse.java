package com.hendersonkleber.weatherforecast.controller.dto;

import com.hendersonkleber.weatherforecast.entity.WeatherCondition;
import com.hendersonkleber.weatherforecast.entity.WindDirection;
import com.hendersonkleber.weatherforecast.repository.dto.CityView;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record CityForecastResponse(
        Long id,
        String name,
        String state,
        String country,
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
        WindDirection windDirection10mDominant
) {
    public static CityForecastResponse from(CityView cityView) {
        return new CityForecastResponse(
                cityView.getId(),
                cityView.getName(),
                cityView.getState(),
                cityView.getCountry(),
                cityView.getDate(),
                cityView.getCondition(),
                cityView.getTemperature2mMax(),
                cityView.getTemperature2mMin(),
                cityView.getApparentTemperatureMax(),
                cityView.getApparentTemperatureMin(),
                cityView.getPrecipitationSum(),
                cityView.getRelativeHumidity2mMin(),
                cityView.getRelativeHumidity2mMax(),
                cityView.getPrecipitationProbabilityMax(),
                cityView.getPrecipitationProbabilityMin(),
                cityView.getSunriseAt(),
                cityView.getSunsetAt(),
                cityView.getWindSpeed10mMax(),
                cityView.getWindDirection10mDominant()
        );
    }
}
