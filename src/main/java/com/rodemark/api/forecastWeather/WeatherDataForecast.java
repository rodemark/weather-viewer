package com.rodemark.api.forecastWeather;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class WeatherDataForecast {
    private String cod;
    private int message;
    private int cnt;
    private List<WeatherEntry> list;
    private City city;
}
