package com.hendersonkleber.weatherforecast.service;

import com.hendersonkleber.weatherforecast.controller.dto.CityForecastResponse;
import com.hendersonkleber.weatherforecast.controller.dto.CityResponse;
import com.hendersonkleber.weatherforecast.controller.dto.WeatherForecastDayResponse;
import com.hendersonkleber.weatherforecast.entity.CityPriority;
import com.hendersonkleber.weatherforecast.entity.WeatherForecastDay;
import com.hendersonkleber.weatherforecast.exception.ResourceNotFoundException;
import com.hendersonkleber.weatherforecast.controller.dto.WeatherForecastResponse;
import com.hendersonkleber.weatherforecast.repository.CityRepository;
import com.hendersonkleber.weatherforecast.repository.WeatherForecastDayRepository;
import com.hendersonkleber.weatherforecast.repository.WeatherForecastHourRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class WeatherForecastService {
    private final Logger log = LoggerFactory.getLogger(WeatherForecastService.class);

    private final CityRepository cityRepository;
    private final WeatherForecastDayRepository weatherForecastDayRepository;
    private final WeatherForecastHourRepository weatherForecastHourRepository;

    private final WeatherForecastSyncService weatherForecastSyncService;

    public WeatherForecastService(CityRepository cityRepository, WeatherForecastDayRepository weatherForecastDayRepository, WeatherForecastHourRepository weatherForecastHourRepository, WeatherForecastSyncService weatherForecastSyncService) {
        this.cityRepository = cityRepository;
        this.weatherForecastDayRepository = weatherForecastDayRepository;
        this.weatherForecastHourRepository = weatherForecastHourRepository;
        this.weatherForecastSyncService = weatherForecastSyncService;
    }

    public List<CityForecastResponse> getCities(List<Long> cityIds) {
        var cities = cityRepository.findAllById(cityIds);

        var citiesWithoutSync = cities.stream()
                .filter(city -> city.getLastSyncAt() == null || city.getLastSyncAt().isBefore(LocalDate.now().atStartOfDay()))
                .toList();

        if (!citiesWithoutSync.isEmpty()) {
            this.log.info("Processing {} cities", citiesWithoutSync.size());

            try {
                this.weatherForecastSyncService.syncCities(citiesWithoutSync);
            } catch (Exception e) {
                this.log.error("Failed to sync cities", e);
            }
        }

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

        var startDate = LocalDate.now().minusDays(1);
        var endDate = LocalDate.now().plusDays(days);

        if (city.getLastSyncAt() == null || city.getLastSyncAt().isBefore(LocalDate.now().atStartOfDay())) {
            var cities = List.of(city);
            this.log.info("Processing {} cities", cities.size());

            try {
                this.weatherForecastSyncService.syncCities(cities);
            } catch (Exception e) {
                this.log.error("Failed to sync cities", e);
            }
        }


        var daysRetrieve = weatherForecastDayRepository.findByCityIdAndDates(cityId, startDate, endDate);
        var daysResponse = daysRetrieve
                .stream()
                .map(d -> {
                    var hoursRetrieve = weatherForecastHourRepository.findByDayIds(List.of(d.getId()));
                    return WeatherForecastDayResponse.from(d, hoursRetrieve);
                })
                .toList();

        return new WeatherForecastResponse(
                CityResponse.from(city),
                daysResponse
        );
    }

    public WeatherForecastResponse getHistory(Long cityId, Integer days) {
        var city = this.cityRepository.findById(cityId)
                .orElseThrow(() -> new ResourceNotFoundException("City not found"));

        var startDate = LocalDate.now().minusDays(days + 1);
        var endDate = LocalDate.now();

        if (city.getLastSyncAt() == null || city.getLastSyncAt().isBefore(LocalDate.now().atStartOfDay())) {
            var cities = List.of(city);
            this.log.info("Processing {} cities", cities.size());

            try {
                this.weatherForecastSyncService.syncCities(cities);
            } catch (Exception e) {
                this.log.error("Failed to sync cities", e);
            }
        }


        var daysRetrieve = weatherForecastDayRepository.findByCityIdAndDates(cityId, startDate, endDate);
        var daysResponse = daysRetrieve
                .stream()
                .map(d -> {
                    var hoursRetrieve = weatherForecastHourRepository.findByDayIds(List.of(d.getId()));
                    return WeatherForecastDayResponse.from(d, hoursRetrieve);
                })
                .toList();

        return new WeatherForecastResponse(
                CityResponse.from(city),
                daysResponse
        );
    }
}
