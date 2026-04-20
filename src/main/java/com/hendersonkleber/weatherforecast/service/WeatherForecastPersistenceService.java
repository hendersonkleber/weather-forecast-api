package com.hendersonkleber.weatherforecast.service;

import com.hendersonkleber.weatherforecast.client.dto.Daily;
import com.hendersonkleber.weatherforecast.client.dto.Hourly;
import com.hendersonkleber.weatherforecast.client.dto.OpenMeteoWeatherForecastResponse;
import com.hendersonkleber.weatherforecast.entity.*;
import com.hendersonkleber.weatherforecast.repository.CityRepository;
import com.hendersonkleber.weatherforecast.repository.WeatherForecastDayRepository;
import com.hendersonkleber.weatherforecast.repository.WeatherForecastHourRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class WeatherForecastPersistenceService {
    private final Logger log = LoggerFactory.getLogger(WeatherForecastPersistenceService.class);
    private final WeatherForecastDayRepository dayRepository;
    private final WeatherForecastHourRepository hourRepository;
    private final CityRepository cityRepository;

    public WeatherForecastPersistenceService(WeatherForecastDayRepository dayRepository, WeatherForecastHourRepository hourRepository, CityRepository cityRepository) {
        this.dayRepository = dayRepository;
        this.hourRepository = hourRepository;
        this.cityRepository = cityRepository;
    }

    @Transactional
    public void persist(List<City> cities, List<OpenMeteoWeatherForecastResponse> response) {
        // lista das cidades ids
        List<Long> cityIds = cities.stream().map(City::getId).toList();

        // lista das datas retornadas pelo openmeteo
        List<LocalDate> dates = response.getFirst().daily().time().stream().map(LocalDate::parse).toList();

        // lista dos dias inseridos no banco por cidade ID e data
        List<WeatherForecastDay> existingDays = this.dayRepository.findByCityIdAndDate(cityIds, dates);

        // Map do id da cidade com a data para facilitar
        Map<String, WeatherForecastDay> existingDaysMap = existingDays.stream().collect(Collectors.toMap(
                d -> d.getCity().getId() + "_" + d.getDate(),
                d -> d
        ));

        List<WeatherForecastDay> daysToSave = new ArrayList<>();

        // iterando cada cidade
        for (int i = 0; i < cities.size(); i++) {
            var city = cities.get(i);
            var forecast = response.get(i);

            // iterando cada data retornada pelo open meteo
            for (int j = 0; j < forecast.daily().time().size(); j++) {
                var day = buildDay(j, forecast.daily(), city, existingDaysMap);
                daysToSave.add(day);
            }
        }

        // Atualizar ou inserir todos os dias processados
        List<WeatherForecastDay> savedDays = this.dayRepository.saveAll(daysToSave);

        // lista dos ids dos dias salvos
        List<Long> dayIds = savedDays.stream().map(WeatherForecastDay::getId).toList();

        // lista das horas inseridas por dia
        List<WeatherForecastHour> existingHours = this.hourRepository.findByDayIds(dayIds);

        // Map do id do dia com hora para facilitar
        Map<String, WeatherForecastHour> existingHoursMap = existingHours.stream().collect(Collectors.toMap(
                h -> h.getWeatherForecastDay().getId() + "_" + h.getHour(),
                h -> h
        ));

        Map<Long, List<WeatherForecastDay>> daysByCityId = savedDays.stream().collect(Collectors.groupingBy(d -> d.getCity().getId()));
        List<WeatherForecastHour> hoursToSave = new ArrayList<>();

        // iterando cada cidade
        for (int i = 0; i < cities.size(); i++) {
            var city = cities.get(i);
            var forecast = response.get(i);
            var cityDays = daysByCityId.getOrDefault(city.getId(), List.of());

            // iterando cada data retornada pelo open meteo
            for (WeatherForecastDay day : cityDays) {
                List<WeatherForecastHour> hours = buildHours(day, forecast.hourly(), existingHoursMap);
                hoursToSave.addAll(hours);
            }
        }

        // Atualizar ou inserir todas as horas processadas
        List<WeatherForecastHour> hoursSaved = this.hourRepository.saveAll(hoursToSave);

        // Atualizar cidades com data e hora da sincronização
        this.cityRepository.updateLastSyncAt(cityIds, LocalDateTime.now());

        log.info("Cities synchronized: {} cities, {} days, {} hours", cities.size(), savedDays.size(), hoursSaved.size());
    }

    private WeatherForecastDay buildDay(int index, Daily daily, City city, Map<String, WeatherForecastDay> existingDaysMap) {
        LocalDate date = LocalDate.parse(daily.time().get(index));
        String key = city.getId() + "_" + date;
        WeatherForecastDay entity = existingDaysMap.getOrDefault(key, new WeatherForecastDay());

        entity.setCity(city);
        entity.setDate(date);
        entity.setCondition(WeatherCondition.fromWmoCode(daily.weatherCode().get(index)));
        entity.setTemperature2mMax(daily.temperature2mMax().get(index));
        entity.setTemperature2mMin(daily.temperature2mMin().get(index));
        entity.setApparentTemperatureMax(daily.apparentTemperatureMax().get(index));
        entity.setApparentTemperatureMin(daily.apparentTemperatureMin().get(index));
        entity.setPrecipitationSum(daily.precipitationSum().get(index));
        entity.setRelativeHumidity2mMin(daily.relativeHumidity2mMin().get(index));
        entity.setRelativeHumidity2mMax(daily.relativeHumidity2mMax().get(index));
        entity.setPrecipitationProbabilityMax(daily.precipitationProbabilityMax().get(index));
        entity.setPrecipitationProbabilityMin(daily.precipitationProbabilityMin().get(index));
        entity.setSunriseAt(LocalDateTime.parse(daily.sunrise().get(index)));
        entity.setSunsetAt(LocalDateTime.parse(daily.sunset().get(index)));
        entity.setWindSpeed10mMax(daily.windSpeed10mMax().get(index));
        entity.setWindDirection10mDominant(WindDirection.fromDegrees(daily.windDirection10mDominant().get(index)));

        return entity;
    }

    private List<WeatherForecastHour> buildHours(WeatherForecastDay day, Hourly hourly, Map<String, WeatherForecastHour> existingHours) {

        List<WeatherForecastHour> hours = new ArrayList<>();

        for (int i = 0; i < hourly.time().size(); i++) {
            if (!hourly.time().get(i).startsWith(day.getDate().toString())) continue;

            LocalTime hour = LocalDateTime.parse(hourly.time().get(i)).toLocalTime();
            String key = day.getId() + "_" + hour;
            WeatherForecastHour entity = existingHours.getOrDefault(key, new WeatherForecastHour());

            entity.setHour(hour);
            entity.setWeatherForecastDay(day);
            entity.setCondition(WeatherCondition.fromWmoCode(hourly.weatherCode().get(i)));
            entity.setCloudCover(hourly.cloudCover().get(i));
            entity.setTemperature2m(hourly.temperature2m().get(i));
            entity.setApparentTemperature(hourly.apparentTemperature().get(i));
            entity.setPrecipitation(hourly.precipitation().get(i));
            entity.setPrecipitationProbability(hourly.precipitationProbability().get(i));
            entity.setRelativeHumidity2m(hourly.relativeHumidity2m().get(i));
            entity.setWindDirection10m(WindDirection.fromDegrees(hourly.windDirection10m().get(i)));
            entity.setWindSpeed10m(hourly.windSpeed10m().get(i));

            hours.add(entity);
        }

        return hours;
    }
}
