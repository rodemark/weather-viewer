package com.rodemark.api.currentWeather;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RainCurrent {
    @JsonProperty("1h")
    private double oneHour;
}
