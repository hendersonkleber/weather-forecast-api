package com.hendersonkleber.weatherforecast.entity;

public enum WeatherCondition {
    CLEAR,
    PARTLY_CLOUDY,
    FOG,
    DRIZZLE,
    FREEZING_DRIZZLE,
    RAIN,
    FREEZING_RAIN,
    SNOW_FALL,
    SNOW_GRAINS,
    RAIN_SHOWERS,
    SNOW_SHOWERS,
    THUNDERSTORM,
    THUNDERSTORM_WITH_HAIL;

    public static WeatherCondition fromWmoCode(int code) {
        return switch (code) {
            case 0 -> CLEAR;
            case 1, 2, 3 -> PARTLY_CLOUDY;
            case 45, 48 -> FOG;
            case 51, 53, 55 -> DRIZZLE;
            case 56, 57 -> FREEZING_DRIZZLE;
            case 61, 63, 65 -> RAIN;
            case 66, 67 -> FREEZING_RAIN;
            case 71, 73, 75 -> SNOW_FALL;
            case 77 -> SNOW_GRAINS;
            case 80, 81, 82 -> RAIN_SHOWERS;
            case 85, 86 -> SNOW_SHOWERS;
            case 95 -> THUNDERSTORM;
            case 96, 99 -> THUNDERSTORM_WITH_HAIL;
            default -> throw new IllegalStateException("Unexpected argument: " + code);
        };
    }
}
