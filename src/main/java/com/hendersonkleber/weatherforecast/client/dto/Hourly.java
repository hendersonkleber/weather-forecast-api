package com.hendersonkleber.weatherforecast.client.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record Hourly(
        @JsonProperty("time")
        List<String> time,

        @JsonProperty("temperature_2m")
        List<Double> temperature2m,

        @JsonProperty("relative_humidity_2m")
        List<Integer> relativeHumidity2m,

        @JsonProperty("apparent_temperature")
        List<Double> apparentTemperature,

        @JsonProperty("precipitation_probability")
        List<Double> precipitationProbability,

        @JsonProperty("precipitation")
        List<Double> precipitation,

        @JsonProperty("weather_code")
        List<Integer> weatherCode,

        @JsonProperty("cloud_cover")
        List<Integer> cloudCover,

        @JsonProperty("wind_speed_10m")
        List<Double> windSpeed10m,

        @JsonProperty("wind_direction_10m")
        List<Integer> windDirection10m
) {
}
