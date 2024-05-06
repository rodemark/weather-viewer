package com.rodemark.api.forecastWeather;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RainForecast {
    @JsonProperty("3h")
    private double threeHour;
}
