package com.hendersonkleber.weatherforecast.scheduler;

import com.hendersonkleber.weatherforecast.entity.City;
import com.hendersonkleber.weatherforecast.repository.CityRepository;
import com.hendersonkleber.weatherforecast.service.WeatherForecastSyncService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class WeatherForecastSyncScheduler {
    private final Logger log = LoggerFactory.getLogger(WeatherForecastSyncScheduler.class);

    private final CityRepository cityRepository;
    private final WeatherForecastSyncService syncService;

    public WeatherForecastSyncScheduler(CityRepository cityRepository, WeatherForecastSyncService syncService) {
        this.cityRepository = cityRepository;
        this.syncService = syncService;
    }

    @Scheduled(fixedRate = 3600000, initialDelay = 1000)
    public void run() {
        this.log.info("Starting weather forecast sync scheduler");

        List<City> cities = this.cityRepository.findAll(PageRequest.of(0, 2)).getContent();
        long pages = Math.round(cities.size() / 2.0);

        for (int i = 0; i < pages; i++) {
            var from = i * 100;
            var to = i == pages - 1 ? cities.size() : (i + 1) * 100;
            var citiesPaged = cities.subList(from, to);
            this.syncService.sync(citiesPaged);
        }

        this.log.info("Finishing weather forecast sync scheduler");
    }
}
