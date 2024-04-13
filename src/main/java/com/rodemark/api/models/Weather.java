package com.rodemark.api.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Weather {
    private Coordinates coordinates;
    private int temp;
    private int pressure;
    private int humidity;
    private String description;
    private int windSpeed;
    private String windDirection;
    private String country;
    private String nameOfCity;
}
