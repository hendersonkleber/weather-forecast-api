package com.hendersonkleber.weatherforecast.repository;

import com.hendersonkleber.weatherforecast.entity.WeatherForecastDay;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WeatherForecastDayRepository extends JpaRepository<WeatherForecastDay, Long> {
}
