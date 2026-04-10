package com.hendersonkleber.weatherforecast.client.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record Daily(
        @JsonProperty("time")
        List<String> time,

        @JsonProperty("weather_code")
        List<Integer> weatherCode,

        @JsonProperty("temperature_2m_max")
        List<Double> temperature2mMax,

        @JsonProperty("temperature_2m_min")
        List<Double> temperature2mMin,

        @JsonProperty("apparent_temperature_max")
        List<Double> apparentTemperatureMax,

        @JsonProperty("apparent_temperature_min")
        List<Double> apparentTemperatureMin,

        @JsonProperty("sunrise")
        List<String> sunrise,

        @JsonProperty("sunset")
        List<String> sunset,

        @JsonProperty("precipitation_sum")
        List<Double> precipitationSum,

        @JsonProperty("relative_humidity_2m_min")
        List<Integer> relativeHumidity2mMin,

        @JsonProperty("relative_humidity_2m_max")
        List<Integer> relativeHumidity2mMax,

        @JsonProperty("precipitation_probability_max")
        List<Double> precipitationProbabilityMax,

        @JsonProperty("precipitation_probability_min")
        List<Double> precipitationProbabilityMin,

        @JsonProperty("wind_speed_10m_max")
        List<Double> windSpeed10mMax,

        @JsonProperty("wind_direction_10m_dominant")
        List<Integer> windDirection10mDominant
) {
}
