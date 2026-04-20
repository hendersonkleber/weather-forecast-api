package com.hendersonkleber.weatherforecast.scheduler;

import com.hendersonkleber.weatherforecast.entity.City;
import com.hendersonkleber.weatherforecast.entity.CityPriority;
import com.hendersonkleber.weatherforecast.repository.CityRepository;
import com.hendersonkleber.weatherforecast.service.WeatherForecastSyncService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class WeatherForecastSyncScheduler {
    private final Logger log = LoggerFactory.getLogger(WeatherForecastSyncScheduler.class);

    private final static int BATCH_SIZE = 50;

    private final CityRepository cityRepository;
    private final WeatherForecastSyncService syncService;

    public WeatherForecastSyncScheduler(CityRepository cityRepository, WeatherForecastSyncService syncService) {
        this.cityRepository = cityRepository;
        this.syncService = syncService;
    }

    @Scheduled(fixedRate = 3600000, initialDelay = 1000)
    public void run() {
        this.log.info("Starting weather forecast sync scheduler");

        var content = this.cityRepository.findByPriority(List.of(CityPriority.HIGH), LocalDateTime.now());

       if (!content.isEmpty()) {
            this.log.info("Processing {} cities", content.size());

            try {
                this.syncService.sync(content);
            }   catch (Exception e){
                this.log.error("Failed to sync cities", e);
            }
        }

        this.log.info("Finishing weather forecast sync scheduler");
    }
}
