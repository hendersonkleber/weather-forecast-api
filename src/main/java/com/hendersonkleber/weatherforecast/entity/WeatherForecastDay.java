package com.hendersonkleber.weatherforecast.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "weather_forecast_day")
public class WeatherForecastDay {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "date", nullable = false)
    private LocalDate date;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "city_id", nullable = false)
    private City city;

    @Enumerated(EnumType.STRING)
    @Column(name = "condition")
    private WeatherCondition condition;

    @Column(name = "temperature_2m_max")
    private Double temperature2mMax;

    @Column(name = "temperature_2m_min")
    private Double temperature2mMin;

    @Column(name = "apparent_temperature_max")
    private Double apparentTemperatureMax;

    @Column(name = "apparent_temperature_min")
    private Double apparentTemperatureMin;

    @Column(name = "precipitation_sum")
    private Double precipitationSum;

    @Column(name = "relative_humidity_2m_min")
    private Integer relativeHumidity2mMin;

    @Column(name = "relative_humidity_2m_max")
    private Integer relativeHumidity2mMax;

    @Column(name = "precipitation_probability_max")
    private Double precipitationProbabilityMax;

    @Column(name = "precipitation_probability_min")
    private Double precipitationProbabilityMin;

    @Column(name = "sunrise_at")
    private LocalDateTime sunriseAt;

    @Column(name = "sunset_at")
    private LocalDateTime sunsetAt;

    @Column(name = "wind_speed_10m_max")
    private Double windSpeed10mMax;

    @Enumerated(EnumType.STRING)
    @Column(name = "wind_direction_10m_dominant")
    private WindDirection windDirection10mDominant;

    public WeatherForecastDay() {
    }

    public WeatherForecastDay(LocalDate date, City city, WeatherCondition condition, Double temperature2mMax, Double temperature2mMin, Double apparentTemperatureMax, Double apparentTemperatureMin, Double precipitationSum, Integer relativeHumidity2mMin, Integer relativeHumidity2mMax, Double precipitationProbabilityMax, Double precipitationProbabilityMin, LocalDateTime sunriseAt, LocalDateTime sunsetAt, Double windSpeed10mMax, WindDirection windDirection10mDominant) {
        this.date = date;
        this.city = city;
        this.condition = condition;
        this.temperature2mMax = temperature2mMax;
        this.temperature2mMin = temperature2mMin;
        this.apparentTemperatureMax = apparentTemperatureMax;
        this.apparentTemperatureMin = apparentTemperatureMin;
        this.precipitationSum = precipitationSum;
        this.relativeHumidity2mMin = relativeHumidity2mMin;
        this.relativeHumidity2mMax = relativeHumidity2mMax;
        this.precipitationProbabilityMax = precipitationProbabilityMax;
        this.precipitationProbabilityMin = precipitationProbabilityMin;
        this.sunriseAt = sunriseAt;
        this.sunsetAt = sunsetAt;
        this.windSpeed10mMax = windSpeed10mMax;
        this.windDirection10mDominant = windDirection10mDominant;
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

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public WeatherCondition getCondition() {
        return condition;
    }

    public void setCondition(WeatherCondition condition) {
        this.condition = condition;
    }

    public Double getTemperature2mMax() {
        return temperature2mMax;
    }

    public void setTemperature2mMax(Double temperature2mMax) {
        this.temperature2mMax = temperature2mMax;
    }

    public Double getTemperature2mMin() {
        return temperature2mMin;
    }

    public void setTemperature2mMin(Double temperature2mMin) {
        this.temperature2mMin = temperature2mMin;
    }

    public Double getApparentTemperatureMax() {
        return apparentTemperatureMax;
    }

    public void setApparentTemperatureMax(Double apparentTemperatureMax) {
        this.apparentTemperatureMax = apparentTemperatureMax;
    }

    public Double getApparentTemperatureMin() {
        return apparentTemperatureMin;
    }

    public void setApparentTemperatureMin(Double apparentTemperatureMin) {
        this.apparentTemperatureMin = apparentTemperatureMin;
    }

    public Double getPrecipitationSum() {
        return precipitationSum;
    }

    public void setPrecipitationSum(Double precipitationSum) {
        this.precipitationSum = precipitationSum;
    }

    public Integer getRelativeHumidity2mMin() {
        return relativeHumidity2mMin;
    }

    public void setRelativeHumidity2mMin(Integer relativeHumidity2mMin) {
        this.relativeHumidity2mMin = relativeHumidity2mMin;
    }

    public Integer getRelativeHumidity2mMax() {
        return relativeHumidity2mMax;
    }

    public void setRelativeHumidity2mMax(Integer relativeHumidity2mMax) {
        this.relativeHumidity2mMax = relativeHumidity2mMax;
    }

    public Double getPrecipitationProbabilityMax() {
        return precipitationProbabilityMax;
    }

    public void setPrecipitationProbabilityMax(Double precipitationProbabilityMax) {
        this.precipitationProbabilityMax = precipitationProbabilityMax;
    }

    public Double getPrecipitationProbabilityMin() {
        return precipitationProbabilityMin;
    }

    public void setPrecipitationProbabilityMin(Double precipitationProbabilityMin) {
        this.precipitationProbabilityMin = precipitationProbabilityMin;
    }

    public LocalDateTime getSunriseAt() {
        return sunriseAt;
    }

    public void setSunriseAt(LocalDateTime sunriseAt) {
        this.sunriseAt = sunriseAt;
    }

    public LocalDateTime getSunsetAt() {
        return sunsetAt;
    }

    public void setSunsetAt(LocalDateTime sunsetAt) {
        this.sunsetAt = sunsetAt;
    }

    public Double getWindSpeed10mMax() {
        return windSpeed10mMax;
    }

    public void setWindSpeed10mMax(Double windSpeed10mMax) {
        this.windSpeed10mMax = windSpeed10mMax;
    }

    public WindDirection getWindDirection10mDominant() {
        return windDirection10mDominant;
    }

    public void setWindDirection10mDominant(WindDirection windDirection10mDominant) {
        this.windDirection10mDominant = windDirection10mDominant;
    }
}
