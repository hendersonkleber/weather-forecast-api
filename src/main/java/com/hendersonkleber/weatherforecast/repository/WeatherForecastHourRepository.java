package com.hendersonkleber.weatherforecast.repository;

import com.hendersonkleber.weatherforecast.entity.WeatherForecastHour;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

public interface WeatherForecastHourRepository extends JpaRepository<WeatherForecastHour, Long> {
    @Query("SELECT w FROM WeatherForecastHour w WHERE w.weatherForecastDay.id IN :dayIds")
    List<WeatherForecastHour> findByDayIds(List<Long> dayIds);
}
