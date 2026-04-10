package com.hendersonkleber.weatherforecast.dto;

import com.hendersonkleber.weatherforecast.entity.City;

public record CityResponse(Long id, String name, String state, String country) {
    public static CityResponse from(City entity) {
        return new CityResponse(entity.getId(), entity.getName(), entity.getState(), entity.getCountry());
    }
}
