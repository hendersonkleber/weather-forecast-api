package com.hendersonkleber.weatherforecast.repository.dto;

import com.hendersonkleber.weatherforecast.entity.WeatherCondition;
import com.hendersonkleber.weatherforecast.entity.WindDirection;

import java.time.LocalDate;
import java.time.LocalDateTime;

public interface CityView {
    Long getId();
    String getName();
    String getState();
    String getCountry();
    LocalDate getDate();
    WeatherCondition getCondition();
    Double getTemperature2mMax();
    Double getTemperature2mMin();
    Double getApparentTemperatureMax();
    Double getApparentTemperatureMin();
    Double getPrecipitationSum();
    Integer getRelativeHumidity2mMin();
    Integer getRelativeHumidity2mMax();
    Double getPrecipitationProbabilityMax();
    Double getPrecipitationProbabilityMin();
    LocalDateTime getSunriseAt();
    LocalDateTime getSunsetAt();
    Double getWindSpeed10mMax();
    WindDirection getWindDirection10mDominant();
}
