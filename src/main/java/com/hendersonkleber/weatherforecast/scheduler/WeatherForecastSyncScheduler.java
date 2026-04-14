package com.hendersonkleber.weatherforecast.scheduler;

import com.hendersonkleber.weatherforecast.entity.City;
import com.hendersonkleber.weatherforecast.repository.CityRepository;
import com.hendersonkleber.weatherforecast.service.WeatherForecastSyncService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

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

        PageRequest pageable = PageRequest.of(0, BATCH_SIZE);
        Page<City> page;

        do {
            page = cityRepository.findAll(pageable);

            if (page.hasContent()) {
                this.log.info("Processing batch {}/{} with {} cities", page.getNumber() + 1, page.getTotalPages(), page.getTotalElements());

                try {
                    this.syncService.sync(page.getContent());
                } catch (Exception e) {
                    this.log.error("Failed to sync batch {}", page.getNumber(), e);
                }
            }

            pageable = pageable.next();
        } while (page.hasNext());

        this.log.info("Finishing weather forecast sync scheduler");
    }
}
