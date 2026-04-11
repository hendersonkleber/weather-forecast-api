package com.hendersonkleber.weatherforecast.service;

import com.hendersonkleber.weatherforecast.client.OpenMeteoClient;
import com.hendersonkleber.weatherforecast.client.dto.Daily;
import com.hendersonkleber.weatherforecast.client.dto.Hourly;
import com.hendersonkleber.weatherforecast.entity.*;
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

@Service
public class WeatherForecastSyncService {
    private final Logger log = LoggerFactory.getLogger(WeatherForecastSyncService.class);

    private final OpenMeteoClient openMeteoClient;
    private final WeatherForecastDayRepository dayRepository;
    private final WeatherForecastHourRepository hourRepository;

    public WeatherForecastSyncService(OpenMeteoClient openMeteoClient, WeatherForecastDayRepository dayRepository, WeatherForecastHourRepository hourRepository) {
        this.openMeteoClient = openMeteoClient;
        this.dayRepository = dayRepository;
        this.hourRepository = hourRepository;
    }

    @Transactional
    public void sync(List<City> cities) {
        var latitudes = cities.stream().map(City::getLatitude).toList();
        var longitudes = cities.stream().map(City::getLongitude).toList();

        var response = this.openMeteoClient.getWeatherForecast(
                latitudes,
                longitudes,
                "weather_code,temperature_2m_max,temperature_2m_min,apparent_temperature_max,apparent_temperature_min,sunrise,sunset,precipitation_sum,relative_humidity_2m_min,relative_humidity_2m_max,precipitation_probability_max,precipitation_probability_min,wind_speed_10m_max,wind_direction_10m_dominant",
                "temperature_2m,relative_humidity_2m,apparent_temperature,precipitation_probability,precipitation,weather_code,cloud_cover,wind_speed_10m,wind_direction_10m",
                "America%2FSao_Paulo",
                7
        );

        for (int i = 0; i < cities.size(); i++) {
            var city = cities.get(i);
            var forecast = response.get(i);

            for (int j = 0; j < forecast.daily().time().size(); j++) {
                var day = upsertDay(j, forecast.daily(), city);
                upsertHour(day, forecast.hourly());
            }
        }
    }

    private WeatherForecastDay upsertDay(int index, Daily daily, City city) {
        LocalDate day = LocalDate.parse(daily.time().get(index));

        return this.dayRepository
                .findByCityAndDate(city.getId(), day)
                .map((entity) -> {
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

                    return this.dayRepository.save(entity);
                })
                .orElseGet(() -> {
                    var dayResponse = new WeatherForecastDay(
                            day,
                            city,
                            WeatherCondition.fromWmoCode(daily.weatherCode().get(index)),
                            daily.temperature2mMax().get(index),
                            daily.temperature2mMin().get(index),
                            daily.apparentTemperatureMax().get(index),
                            daily.apparentTemperatureMin().get(index),
                            daily.precipitationSum().get(index),
                            daily.relativeHumidity2mMin().get(index),
                            daily.relativeHumidity2mMax().get(index),
                            daily.precipitationProbabilityMax().get(index),
                            daily.precipitationProbabilityMin().get(index),
                            LocalDateTime.parse(daily.sunrise().get(index)),
                            LocalDateTime.parse(daily.sunset().get(index)),
                            daily.windSpeed10mMax().get(index),
                            WindDirection.fromDegrees(daily.windDirection10mDominant().get(index))
                    );

                    return this.dayRepository.save(dayResponse);
                });
    }

    private void upsertHour(WeatherForecastDay day, Hourly hourly) {
        List<Integer> indexes = new ArrayList<>();

        for (int i = 0; i < hourly.time().size(); i++) {
            if (hourly.time().get(i).startsWith(day.getDate().toString())) {
                indexes.add(i);
            }
        }

        for (var index : indexes) {
            var hour = LocalDateTime.parse(hourly.time().get(index));

            this.hourRepository
                    .findByWeatherForecastDayIdAndHour(day.getId(), hour.toLocalTime())
                    .map(entity -> {
                        entity.setCondition(WeatherCondition.fromWmoCode(hourly.weatherCode().get(index)));
                        entity.setCloudCover(hourly.cloudCover().get(index));
                        entity.setTemperature2m(hourly.temperature2m().get(index));
                        entity.setApparentTemperature(hourly.apparentTemperature().get(index));
                        entity.setPrecipitation(hourly.precipitation().get(index));
                        entity.setPrecipitationProbability(hourly.precipitationProbability().get(index));
                        entity.setRelativeHumidity2m(hourly.relativeHumidity2m().get(index));
                        entity.setWindDirection10m(WindDirection.fromDegrees(hourly.windDirection10m().get(index)));
                        entity.setWindSpeed10m(hourly.windSpeed10m().get(index));

                        return this.hourRepository.save(entity);
                    })
                    .orElseGet(() -> {
                        var entity = new WeatherForecastHour(
                                hour.toLocalTime(),
                                day,
                                WeatherCondition.fromWmoCode(hourly.weatherCode().get(index)),
                                hourly.cloudCover().get(index),
                                hourly.temperature2m().get(index),
                                hourly.apparentTemperature().get(index),
                                hourly.precipitation().get(index),
                                hourly.precipitationProbability().get(index),
                                hourly.relativeHumidity2m().get(index),
                                WindDirection.fromDegrees(hourly.windDirection10m().get(index)),
                                hourly.windSpeed10m().get(index)
                        );

                        return this.hourRepository.save(entity);
                    });
        }
    }
}
