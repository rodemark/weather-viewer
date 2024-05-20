package com.rodemark.api.others;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class WeatherDaily extends WeatherCurrent{
    private LocalDateTime dateTime;
}
