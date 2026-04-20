package com.hendersonkleber.weatherforecast.entity;

import com.hendersonkleber.weatherforecast.util.TextUtil;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "city")
public class City {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "last_sync_at")
    private LocalDateTime lastSyncAt;

    @Enumerated(EnumType.STRING)
    @Column(name = "priority")
    private CityPriority priority;

    @Column(name = "name", length = 120, nullable = false)
    private String name;

    @Column(name = "name_normalized", length = 120, nullable = false)
    private String nameNormalized;

    @Column(name = "latitude", precision = 10, scale = 8, nullable = false)
    private BigDecimal latitude;

    @Column(name = "longitude", precision = 11, scale = 8, nullable = false)
    private BigDecimal longitude;

    @Column(name = "state", length = 120)
    private String state;

    @Column(name = "country", length = 120, nullable = false)
    private String country;

    public City() {
    }

    public City(LocalDateTime createdAt, Long id, LocalDateTime updatedAt, LocalDateTime lastSyncAt, CityPriority priority, String name, String nameNormalized, BigDecimal latitude, BigDecimal longitude, String state, String country) {
        this.createdAt = createdAt;
        this.id = id;
        this.updatedAt = updatedAt;
        this.lastSyncAt = lastSyncAt;
        this.priority = priority;
        this.name = name;
        this.nameNormalized = nameNormalized;
        this.latitude = latitude;
        this.longitude = longitude;
        this.state = state;
        this.country = country;
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

    public LocalDateTime getLastSyncAt() {
        return lastSyncAt;
    }

    public void setLastSyncAt(LocalDateTime lastSyncAt) {
        this.lastSyncAt = lastSyncAt;
    }

    public CityPriority getPriority() {
        return priority;
    }

    public void setPriority(CityPriority priority) {
        this.priority = priority;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNameNormalized() {
        return nameNormalized;
    }

    public void setNameNormalized(String nameNormalized) {
        this.nameNormalized = nameNormalized;
    }

    public BigDecimal getLatitude() {
        return latitude;
    }

    public void setLatitude(BigDecimal latitude) {
        this.latitude = latitude;
    }

    public BigDecimal getLongitude() {
        return longitude;
    }

    public void setLongitude(BigDecimal longitude) {
        this.longitude = longitude;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
