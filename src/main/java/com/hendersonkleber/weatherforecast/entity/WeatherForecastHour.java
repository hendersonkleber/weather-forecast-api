package com.hendersonkleber.weatherforecast.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Table(name = "weather_forecast_hour")
public class WeatherForecastHour {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "hour", nullable = false)
    private LocalTime hour;

    @ManyToOne
    @JoinColumn(name = "weather_forecast_day_id", nullable = false)
    private WeatherForecastDay weatherForecastDay;

    @Enumerated(EnumType.STRING)
    @Column(name = "condition")
    private WeatherCondition condition;

    @Column(name = "cloud_cover")
    private Integer cloudCover;

    @Column(name = "temperature_2m")
    private Double temperature2m;

    @Column(name = "apparent_temperature")
    private Double apparentTemperature;

    @Column(name = "precipitation")
    private Double precipitation;

    @Column(name = "precipitation_probability")
    private Double precipitationProbability;

    @Column(name = "relative_humidity_2m")
    private Integer relativeHumidity2m;

    @Enumerated(EnumType.STRING)
    @Column(name = "wind_direction_10m")
    private WindDirection windDirection10m;

    @Column(name = "wind_speed_10m")
    private Double windSpeed10m;

    public WeatherForecastHour() {
    }

    public WeatherForecastHour(LocalTime hour, WeatherForecastDay weatherForecastDay, WeatherCondition condition, Integer cloudCover, Double temperature2m, Double apparentTemperature, Double precipitation, Double precipitationProbability, Integer relativeHumidity2m, WindDirection windDirection10m, Double windSpeed10m) {
        this.hour = hour;
        this.weatherForecastDay = weatherForecastDay;
        this.condition = condition;
        this.cloudCover = cloudCover;
        this.temperature2m = temperature2m;
        this.apparentTemperature = apparentTemperature;
        this.precipitation = precipitation;
        this.precipitationProbability = precipitationProbability;
        this.relativeHumidity2m = relativeHumidity2m;
        this.windDirection10m = windDirection10m;
        this.windSpeed10m = windSpeed10m;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public LocalTime getHour() {
        return hour;
    }

    public void setHour(LocalTime hour) {
        this.hour = hour;
    }

    public WeatherForecastDay getWeatherForecastDay() {
        return weatherForecastDay;
    }

    public void setWeatherForecastDay(WeatherForecastDay weatherForecastDay) {
        this.weatherForecastDay = weatherForecastDay;
    }

    public WeatherCondition getCondition() {
        return condition;
    }

    public void setCondition(WeatherCondition condition) {
        this.condition = condition;
    }

    public Integer getCloudCover() {
        return cloudCover;
    }

    public void setCloudCover(Integer cloudCover) {
        this.cloudCover = cloudCover;
    }

    public Double getTemperature2m() {
        return temperature2m;
    }

    public void setTemperature2m(Double temperature2m) {
        this.temperature2m = temperature2m;
    }

    public Double getApparentTemperature() {
        return apparentTemperature;
    }

    public void setApparentTemperature(Double apparentTemperature) {
        this.apparentTemperature = apparentTemperature;
    }

    public Double getPrecipitation() {
        return precipitation;
    }

    public void setPrecipitation(Double precipitation) {
        this.precipitation = precipitation;
    }

    public Double getPrecipitationProbability() {
        return precipitationProbability;
    }

    public void setPrecipitationProbability(Double precipitationProbability) {
        this.precipitationProbability = precipitationProbability;
    }

    public Integer getRelativeHumidity2m() {
        return relativeHumidity2m;
    }

    public void setRelativeHumidity2m(Integer relativeHumidity2m) {
        this.relativeHumidity2m = relativeHumidity2m;
    }

    public WindDirection getWindDirection10m() {
        return windDirection10m;
    }

    public void setWindDirection10m(WindDirection windDirection10m) {
        this.windDirection10m = windDirection10m;
    }

    public Double getWindSpeed10m() {
        return windSpeed10m;
    }

    public void setWindSpeed10m(Double windSpeed10m) {
        this.windSpeed10m = windSpeed10m;
    }
}
