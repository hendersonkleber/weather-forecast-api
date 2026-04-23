package com.hendersonkleber.weatherforecast.repository;

import com.hendersonkleber.weatherforecast.entity.City;
import com.hendersonkleber.weatherforecast.entity.CityPriority;
import com.hendersonkleber.weatherforecast.repository.dto.CityView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface CityRepository extends JpaRepository<City, Long> {
    @Query("SELECT c FROM City c WHERE c.nameNormalized LIKE CONCAT('%', :query, '%')")
    List<City> search(String query);

    @Query("SELECT c FROM City c WHERE c.priority IN :priorities AND (c.lastSyncAt IS NULL OR c.lastSyncAt < :lastSync)")
    List<City> getByPriorityAndLastSync(List<CityPriority> priorities, LocalDateTime lastSync);

    @Modifying
    @Query("UPDATE City c SET c.lastSyncAt = :now WHERE c.id IN :cityIds")
    void updateLastSyncAt(List<Long> cityIds, LocalDateTime now);
}
