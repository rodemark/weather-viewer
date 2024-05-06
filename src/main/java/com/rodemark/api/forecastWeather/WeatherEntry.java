package com.rodemark.api.forecastWeather;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.rodemark.api.general.Clouds;
import com.rodemark.api.general.Weather;
import com.rodemark.api.general.Wind;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class WeatherEntry {
    private long dt;
    private MainForecast main;
    private List<Weather> weather;
    private Clouds clouds;
    private Wind wind;
    private int visibility;
    private double pop;
    private RainForecast rain;
    private SysForecast sys;
    @JsonProperty("dt_txt")
    private String dtTxt;
}
