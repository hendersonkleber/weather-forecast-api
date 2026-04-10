package com.hendersonkleber.weatherforecast.repository;

import org.springframework.data.jpa.repository.JpaRepository;

public interface WeatherForecastHour extends JpaRepository<WeatherForecastHour, Long> {
}
