package com.hendersonkleber.weatherforecast.service;

import com.hendersonkleber.weatherforecast.controller.dto.CityForecastResponse;
import com.hendersonkleber.weatherforecast.controller.dto.CityResponse;
import com.hendersonkleber.weatherforecast.controller.dto.WeatherForecastDayResponse;
import com.hendersonkleber.weatherforecast.entity.CityPriority;
import com.hendersonkleber.weatherforecast.entity.WeatherForecastDay;
import com.hendersonkleber.weatherforecast.exception.ResourceNotFoundException;
import com.hendersonkleber.weatherforecast.repository.dto.CityView;
import com.hendersonkleber.weatherforecast.controller.dto.WeatherForecastResponse;
import com.hendersonkleber.weatherforecast.repository.CityRepository;
import com.hendersonkleber.weatherforecast.repository.WeatherForecastDayRepository;
import com.hendersonkleber.weatherforecast.repository.WeatherForecastHourRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class WeatherForecastService {
    private final Logger log = LoggerFactory.getLogger(WeatherForecastService.class);

    private final CityRepository cityRepository;
    private final WeatherForecastDayRepository weatherForecastDayRepository;
    private final WeatherForecastHourRepository weatherForecastHourRepository;

    public WeatherForecastService(CityRepository cityRepository, WeatherForecastDayRepository weatherForecastDayRepository, WeatherForecastHourRepository weatherForecastHourRepository) {
        this.cityRepository = cityRepository;
        this.weatherForecastDayRepository = weatherForecastDayRepository;
        this.weatherForecastHourRepository = weatherForecastHourRepository;
    }

    public List<CityForecastResponse> getCities(List<Long> cityIds) {
        return weatherForecastDayRepository.getByIdWithForecast(cityIds, LocalDate.now())
                .stream()
                .map(CityForecastResponse::from)
                .toList();
    }

    public List<CityForecastResponse> getPopularCities() {
        return weatherForecastDayRepository.getByPriorityWithForecast(List.of(CityPriority.HIGH), LocalDate.now())
                .stream()
                .map(CityForecastResponse::from)
                .toList();
    }

    public WeatherForecastResponse getForecast(Long cityId, Integer days) {
        var city = this.cityRepository.findById(cityId)
                .orElseThrow(() -> new ResourceNotFoundException("City not found"));

        var dates = new ArrayList<LocalDate>(1);

        var dh = this.weatherForecastDayRepository.findByCityIdAndDate(
                        List.of(cityId),
                        dates
                )
                .stream()
                .map(day -> {
                    var hours = this.weatherForecastHourRepository.findByDayIds(List.of(day.getId()));
                    return WeatherForecastDayResponse.from(day, hours);
                }).toList();

        return new WeatherForecastResponse(
                CityResponse.from(city),
                dh
        );
    }

    public WeatherForecastResponse getHistory(Long cityId, Integer days) {
        return null;
    }

}
