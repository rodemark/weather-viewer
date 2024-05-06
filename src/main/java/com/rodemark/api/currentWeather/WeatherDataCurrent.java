package com.rodemark.api.currentWeather;

import com.rodemark.api.general.Clouds;
import com.rodemark.api.general.Coord;
import com.rodemark.api.general.Weather;
import com.rodemark.api.general.Wind;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WeatherDataCurrent {
    private Coord coord;
    private Weather[] weather;
    private String base;
    private MainCurrent main;
    private int visibility;
    private Wind wind;
    private RainCurrent rain;
    private Clouds clouds;
    private long dt;
    private SysCurrent sys;
    private int timezone;
    private int id;
    private String name;
    private int cod;

}
