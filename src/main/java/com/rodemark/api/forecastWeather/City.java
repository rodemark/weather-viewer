package com.rodemark.api.forecastWeather;

import com.rodemark.api.general.Coord;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class City {
    private int id;
    private String name;
    private Coord coord;
    private String country;
    private int population;
    private int timezone;
    private long sunrise;
    private long sunset;
}
