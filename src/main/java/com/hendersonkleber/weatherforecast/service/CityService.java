package com.hendersonkleber.weatherforecast.service;

import com.hendersonkleber.weatherforecast.entity.City;
import com.hendersonkleber.weatherforecast.repository.CityRepository;
import com.hendersonkleber.weatherforecast.util.TextUtil;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CityService {
    private final CityRepository cityRepository;

    public CityService(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

    public List<City> search(String query) {
        query = TextUtil.normalize(query);
        return this.cityRepository.search(query);
    }
}
