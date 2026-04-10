package com.hendersonkleber.weatherforecast.controller;

import com.hendersonkleber.weatherforecast.dto.CityResponse;
import com.hendersonkleber.weatherforecast.service.CityService;
import jakarta.validation.constraints.NotBlank;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/city")
public class CityController {
    private final CityService cityService;

    public CityController(CityService cityService) {
        this.cityService = cityService;
    }

    @GetMapping(path = "/search")
    public ResponseEntity<List<CityResponse>> search(
            @RequestParam(name = "q")
            @NotBlank(message = "Query is required") String query
    ) {
        var cities = cityService.search(query).stream().map(CityResponse::from).toList();
        return ResponseEntity.ok(cities);
    }
}
