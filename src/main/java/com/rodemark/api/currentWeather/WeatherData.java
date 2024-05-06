package com.rodemark.api.currentWeather;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WeatherData {
    private Coord coord;
    private Weather[] weather;
    private String base;
    private Main main;
    private int visibility;
    private Wind wind;
    private Rain rain;
    private Clouds clouds;
    private long dt;
    private Sys sys;
    private int timezone;
    private int id;
    private String name;
    private int cod;

}
