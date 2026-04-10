package com.hendersonkleber.weatherforecast.repository;

import com.hendersonkleber.weatherforecast.entity.WeatherForecastHour;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WeatherForecastHourRepository extends JpaRepository<WeatherForecastHour, Long> {
}
