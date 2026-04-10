package com.hendersonkleber.weatherforecast.client.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record DailyUnits(
        @JsonProperty("time")
        String time,

        @JsonProperty("weather_code")
        String weatherCode,

        @JsonProperty("temperature_2m_max")
        String temperature2mMax,

        @JsonProperty("temperature_2m_min")
        String temperature2mMin,

        @JsonProperty("apparent_temperature_max")
        String apparentTemperatureMax,

        @JsonProperty("apparent_temperature_min")
        String apparentTemperatureMin,

        @JsonProperty("sunrise")
        String sunrise,

        @JsonProperty("sunset")
        String sunset,

        @JsonProperty("precipitation_sum")
        String precipitationSum,

        @JsonProperty("relative_humidity_2m_min")
        String relativeHumidity2mMin,

        @JsonProperty("relative_humidity_2m_max")
        String relativeHumidity2mMax,

        @JsonProperty("precipitation_probability_max")
        String precipitationProbabilityMax,

        @JsonProperty("precipitation_probability_min")
        String precipitationProbabilityMin,

        @JsonProperty("wind_speed_10m_max")
        String windSpeed10mMax,

        @JsonProperty("wind_direction_10m_dominant")
        String windDirection10mDominant
) {
}
