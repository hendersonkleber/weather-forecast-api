package com.hendersonkleber.weatherforecast;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class WeatherforecastApplication {

	static void main(String[] args) {
		SpringApplication.run(WeatherforecastApplication.class, args);
	}

}
