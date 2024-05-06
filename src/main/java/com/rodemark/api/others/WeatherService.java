package com.rodemark.api.others;


import com.rodemark.api.currentWeather.WeatherDataCurrent;
import com.rodemark.api.forecastWeather.WeatherDataForecast;
import com.rodemark.api.forecastWeather.WeatherEntry;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class WeatherService {

    public static WeatherCurrent toWeatherCurrentFromWeatherDataCurrent(WeatherDataCurrent weatherDataCurrent){
        WeatherCurrent weatherCurrent = new WeatherCurrent();

        weatherCurrent.setCoord(weatherDataCurrent.getCoord());
        weatherCurrent.setDescription(weatherDataCurrent.getWeather()[0].getDescription());
        weatherCurrent.setCountry(weatherDataCurrent.getSys().getCountry());
        weatherCurrent.setNameOfCity(weatherDataCurrent.getName());
        weatherCurrent.setTemp(conversionTemperature(weatherDataCurrent.getMain().getTemp()));
        weatherCurrent.setPressure(conversionPressure(weatherDataCurrent.getMain().getPressure()));
        weatherCurrent.setWindDirection(conversionWindDirections(weatherDataCurrent.getWind().getDeg()));
        weatherCurrent.setWindSpeed((int) weatherDataCurrent.getWind().getSpeed());
        weatherCurrent.setHumidity(weatherDataCurrent.getMain().getHumidity());

        return weatherCurrent;
    }

    public static List<WeatherDaily> toWeatherDailyFromWeatherDataForecast(WeatherDataForecast weatherDataForecast) {
        List<WeatherDaily> weatherDailyList = new ArrayList<>();

        for (int i = 0; i < weatherDataForecast.getList().size(); i++) {
            WeatherEntry weatherEntry = weatherDataForecast.getList().get(i);
            WeatherDaily weatherDaily = new WeatherDaily();
            weatherDaily.setDateTime(toLocalDateTime(weatherEntry.getDtTxt()));
            weatherDaily.setTemp(conversionTemperature(weatherEntry.getMain().getTemp()));
            weatherDaily.setPressure(conversionPressure(weatherEntry.getMain().getPressure()));
            weatherDaily.setHumidity(weatherEntry.getMain().getHumidity());
            weatherDaily.setDescription(weatherEntry.getWeather().getFirst().getDescription());
            weatherDaily.setWindDirection(conversionWindDirections(weatherEntry.getWind().getDeg()));
            weatherDaily.setWindSpeed((int) weatherEntry.getWind().getSpeed());
            weatherDaily.setNameOfCity(weatherDataForecast.getCity().getName());
            weatherDaily.setCountry(weatherDataForecast.getCity().getCountry());
            weatherDaily.setCoord(weatherDataForecast.getCity().getCoord());

            weatherDailyList.add(weatherDaily);
        }

        return weatherDailyList;
    }


    private static LocalDateTime toLocalDateTime(String dateTime) {
        String[] parts = dateTime.split(" ");
        String dateString = parts[0];
        String timeString = parts[1];

        LocalDate date = LocalDate.parse(dateString);
        LocalTime time = LocalTime.parse(timeString);

        return LocalDateTime.of(date, time);
    }


    private static int conversionTemperature(double temp){
        temp -= 273;

        return (int) Math.round(temp);
    }

    private static int conversionPressure(double pressure){
        double k = 0.750061683;
        return (int) Math.round(pressure * k);
    }

    private static String conversionWindDirections(double deg) {
        if ((deg >= 11.25 && deg < 33.75) || (deg >= 348.75 && deg <= 360)) {
            return "North-Northeast";
        } else if (deg >= 33.75 && deg < 56.25) {
            return "Northeast";
        } else if (deg >= 56.25 && deg < 78.75) {
            return "East-Northeast";
        } else if (deg >= 78.75 && deg < 101.25) {
            return "East";
        } else if (deg >= 101.25 && deg < 123.75) {
            return "East-Southeast";
        } else if (deg >= 123.75 && deg < 146.25) {
            return "Southeast";
        } else if (deg >= 146.25 && deg < 168.75) {
            return "South-Southeast";
        } else if (deg >= 168.75 && deg < 191.25) {
            return "South";
        } else if (deg >= 191.25 && deg < 213.75) {
            return "South-Southwest";
        } else if (deg >= 213.75 && deg < 236.25) {
            return "Southwest";
        } else if (deg >= 236.25 && deg < 258.75) {
            return "West-Southwest";
        } else if (deg >= 258.75 && deg < 281.25) {
            return "West";
        } else if (deg >= 281.25 && deg < 303.75) {
            return "West-Northwest";
        } else if (deg >= 303.75 && deg < 326.25) {
            return "Northwest";
        } else if (deg >= 326.25 && deg < 348.75) {
            return "North-Northwest";
        } else {
            return "North";
        }
    }
}
