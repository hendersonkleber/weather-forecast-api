package com.hendersonkleber.weatherforecast.repository;

import com.hendersonkleber.weatherforecast.entity.WeatherForecastDay;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface WeatherForecastDayRepository extends JpaRepository<WeatherForecastDay, Long> {
    @Query("SELECT w FROM WeatherForecastDay w WHERE w.city.id IN :cityIds AND w.date IN :dates")
    List<WeatherForecastDay> findByCityIdAndDate(List<Long> cityIds, List<LocalDate> dates);
}
