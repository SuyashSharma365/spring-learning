package com.suyash.springlearning.controller;


import com.suyash.springlearning.services.WeatherService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/weather")
public class WeatherController {

    @Autowired
    WeatherService weatherService;

    @GetMapping("/{city}")
    public ResponseEntity<?> getWeather(@PathVariable String city){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        log.info("User {} requested for weather api for city {}", authentication.getName() , city);
        try {
            double temp = weatherService.getTemperature(city);
            System.out.println("API URL = " + temp);

            return ResponseEntity.ok(
                    Map.of(
                            "city", city,
                            "temperature", temp,
                            "unit", "C"
                    )
            );
        }
        catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(
                    Map.of("error", e.getMessage())
            );
        }
    }

}
