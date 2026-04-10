package com.hendersonkleber.weatherforecast.entity;

import com.hendersonkleber.weatherforecast.util.TextUtil;
import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "city")
public class City {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

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

    public City(String name, BigDecimal latitude, BigDecimal longitude, String state, String country) {
        this.name = name;
        this.nameNormalized = TextUtil.normalize(name);
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
