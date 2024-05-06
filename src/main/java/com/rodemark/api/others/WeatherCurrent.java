package com.rodemark.api.others;

import com.rodemark.api.general.Coord;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WeatherCurrent {
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
