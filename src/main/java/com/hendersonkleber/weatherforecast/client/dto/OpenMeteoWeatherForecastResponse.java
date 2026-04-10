package com.hendersonkleber.weatherforecast.client.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record OpenMeteoWeatherForecastResponse(
        @JsonProperty("latitude")
        double latitude,

        @JsonProperty("longitude")
        double longitude,

        @JsonProperty("elevation")
        double elevation,

        @JsonProperty("generationtime_ms")
        double generationtimeMs,

        @JsonProperty("timezone")
        String timezone,

        @JsonProperty("timezone_abbreviation")
        String timezoneAbbreviation,

        @JsonProperty("utc_offset_seconds")
        int utcOffsetSeconds,

        @JsonProperty("hourly_units")
        HourlyUnits hourlyUnits,

        @JsonProperty("hourly")
        Hourly hourly,

        @JsonProperty("daily_units")
        DailyUnits dailyUnits,

        @JsonProperty("daily")
        Daily daily
) {

}
