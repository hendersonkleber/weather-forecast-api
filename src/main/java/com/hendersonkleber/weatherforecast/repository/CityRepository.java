package com.hendersonkleber.weatherforecast.repository;

import com.hendersonkleber.weatherforecast.entity.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CityRepository extends JpaRepository<City, Long> {
    @Query("SELECT c FROM City c WHERE c.nameNormalized LIKE CONCAT('%', :query, '%')")
    List<City> search(String query);
}
