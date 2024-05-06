package com.rodemark.api.others;


import com.rodemark.api.currentWeather.WeatherData;

public class WeatherService {

    public static WeatherRedesigned weatherTransfer(WeatherData weatherData){
        WeatherRedesigned weatherRedesigned = new WeatherRedesigned();

        weatherRedesigned.setCoord(weatherData.getCoord());
        weatherRedesigned.setDescription(weatherData.getWeather()[0].getDescription());
        weatherRedesigned.setCountry(weatherData.getSys().getCountry());
        weatherRedesigned.setNameOfCity(weatherData.getName());
        weatherRedesigned.setTemp(conversionTemperature(weatherData.getMain().getTemp()));
        weatherRedesigned.setPressure(conversionPressure(weatherData.getMain().getPressure()));
        weatherRedesigned.setWindDirection(translationToCardinalDirections(weatherData.getWind().getDeg()));
        weatherRedesigned.setWindSpeed((int) weatherData.getWind().getSpeed());
        weatherRedesigned.setHumidity(weatherData.getMain().getHumidity());

        return weatherRedesigned;
    }

    private static int conversionTemperature(double temp){
        temp -= 273;

        return (int) Math.round(temp);
    }

    private static int conversionPressure(double pressure){
        double k = 0.750061683;
        return (int) Math.round(pressure * k);
    }

    private static String translationToCardinalDirections(double deg) {
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
