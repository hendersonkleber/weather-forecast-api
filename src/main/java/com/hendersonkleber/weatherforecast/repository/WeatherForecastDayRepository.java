package com.hendersonkleber.weatherforecast.repository;

import com.hendersonkleber.weatherforecast.entity.WeatherForecastDay;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.Optional;

public interface WeatherForecastDayRepository extends JpaRepository<WeatherForecastDay, Long> {
    @Query("SELECT w FROM WeatherForecastDay w WHERE w.city.id = :cityId AND w.date = :date")
    Optional<WeatherForecastDay> findByCityAndDate(Long cityId, LocalDate date);
}
