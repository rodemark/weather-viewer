package com.rodemark.api.others;

import com.rodemark.api.general.Coord;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class WeatherDaily {
    private LocalDateTime dateTime;
    private int temp;
    private int windSpeed;
    private int humidity;
    private String description;
    private String windDirection;
    private String nameOfCity;
    private String country;
    private Coord coord;
    private int pressure;
}
