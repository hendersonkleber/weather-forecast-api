package com.hendersonkleber.weatherforecast.scheduler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class WeatherForecastSyncScheduler {
    private final Logger log = LoggerFactory.getLogger(WeatherForecastSyncScheduler.class);

    @Scheduled(cron = "0 */1 * * * *")
    public void run() {
        this.log.info("Running weather forecast sync scheduler");
    }
}
