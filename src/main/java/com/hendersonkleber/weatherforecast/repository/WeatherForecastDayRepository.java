package com.hendersonkleber.weatherforecast.repository;

import com.hendersonkleber.weatherforecast.entity.CityPriority;
import com.hendersonkleber.weatherforecast.entity.WeatherForecastDay;
import com.hendersonkleber.weatherforecast.repository.dto.CityView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface WeatherForecastDayRepository extends JpaRepository<WeatherForecastDay, Long> {
    @Query("SELECT w FROM WeatherForecastDay w WHERE w.city.id IN :cityIds AND w.date IN :dates")
    List<WeatherForecastDay> findByCityIdAndDate(List<Long> cityIds, List<LocalDate> dates);

    @Query("""
                        SELECT
                            c.id AS id,
                            c.name AS name,
                            c.state AS state,
                            c.country AS country,
                            wfd.date AS date,
                            wfd.condition AS condition,
                            wfd.temperature2mMax AS temperature2mMax,
                            wfd.temperature2mMin AS temperature2mMin,
                            wfd.apparentTemperatureMax AS apparentTemperatureMax,
                            wfd.apparentTemperatureMin AS apparentTemperatureMin,
                            wfd.precipitationSum AS precipitationSum,
                            wfd.relativeHumidity2mMin AS relativeHumidity2mMin,
                            wfd.relativeHumidity2mMax AS relativeHumidity2mMax,
                            wfd.precipitationProbabilityMax AS precipitationProbabilityMax,
                            wfd.precipitationProbabilityMin AS precipitationProbabilityMin,
                            wfd.sunriseAt AS sunriseAt,
                            wfd.sunsetAt AS sunsetAt,
                            wfd.windSpeed10mMax AS windSpeed10mMax,
                            wfd.windDirection10mDominant AS windDirection10mDominant
                        FROM WeatherForecastDay wfd
                        JOIN wfd.city c
                        WHERE c.id IN :cityIds AND wfd.date = :date
            """)
    List<CityView> getByIdWithForecast(List<Long> cityIds, LocalDate date);

    @Query("""
                        SELECT
                            c.id AS id,
                            c.name AS name,
                            c.state AS state,
                            c.country AS country,
                            wfd.date AS date,
                            wfd.condition AS condition,
                            wfd.temperature2mMax AS temperature2mMax,
                            wfd.temperature2mMin AS temperature2mMin,
                            wfd.apparentTemperatureMax AS apparentTemperatureMax,
                            wfd.apparentTemperatureMin AS apparentTemperatureMin,
                            wfd.precipitationSum AS precipitationSum,
                            wfd.relativeHumidity2mMin AS relativeHumidity2mMin,
                            wfd.relativeHumidity2mMax AS relativeHumidity2mMax,
                            wfd.precipitationProbabilityMax AS precipitationProbabilityMax,
                            wfd.precipitationProbabilityMin AS precipitationProbabilityMin,
                            wfd.sunriseAt AS sunriseAt,
                            wfd.sunsetAt AS sunsetAt,
                            wfd.windSpeed10mMax AS windSpeed10mMax,
                            wfd.windDirection10mDominant AS windDirection10mDominant
                        FROM WeatherForecastDay wfd
                        JOIN wfd.city c
                        WHERE c.priority IN :cityPriorities AND wfd.date = :date
            """)
    List<CityView> getByPriorityWithForecast(List<CityPriority> cityPriorities, LocalDate date);
}
