package com.rodemark;

import com.rodemark.repositories.LocationRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;

@SpringBootApplication
public class WeatherViewerApplication {

    public static void main(String[] args) {
        SpringApplication.run(WeatherViewerApplication.class, args);
    }

}
