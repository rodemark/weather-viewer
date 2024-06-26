package com.rodemark.api.currentWeather;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SysCurrent {
    private int type;
    private int id;
    private String country;
    private long sunrise;
    private long sunset;
}
