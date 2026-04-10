package com.hendersonkleber.weatherforecast.client.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record HourlyUnits(
        @JsonProperty("time")
        String time,

        @JsonProperty("temperature_2m")
        String temperature2m,

        @JsonProperty("relative_humidity_2m")
        String relativeHumidity2m,

        @JsonProperty("apparent_temperature")
        String apparentTemperature,

        @JsonProperty("precipitation_probability")
        String precipitationProbability,

        @JsonProperty("precipitation")
        String precipitation,

        @JsonProperty("weather_code")
        String weatherCode,

        @JsonProperty("cloud_cover")
        String cloudCover,

        @JsonProperty("wind_speed_10m")
        String windSpeed10m,

        @JsonProperty("wind_direction_10m")
        String windDirection10m
) {
}
