package com.rodemark.api.others;

import com.rodemark.api.currentWeather.Coord;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WeatherRedesigned {
    private Coord coord;
    private int temp;
    private int pressure;
    private int humidity;
    private String description;
    private int windSpeed;
    private String windDirection;
    private String country;
    private String nameOfCity;
}
