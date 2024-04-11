package com.rodemark.api.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Weather {
    private Coordinates coordinates;

    // температура в кельвинах
    private double temp;
    private double pressure;
    private double humidity;
    private String description;
    private double windSpeed;
    private double windDirection;
    private String country;
    private String nameOfCity;
}
