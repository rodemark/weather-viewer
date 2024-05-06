package com.rodemark.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rodemark.api.general.Coord;
import com.rodemark.api.currentWeather.WeatherDataCurrent;
import com.rodemark.api.forecastWeather.WeatherDataForecast;
import com.rodemark.api.others.WeatherCurrent;
import com.rodemark.api.others.WeatherDaily;
import com.rodemark.api.others.WeatherService;
import com.rodemark.models.Location;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

@Service
public class ApiService {
    private final String API_KEY = "e9e9403064fef65c39e72895e8f83b93";
    private final String API_URI_CURRENT = "https://api.openweathermap.org/data/2.5/weather";
    private final String API_URI_FORECAST = "https://api.openweathermap.org/data/2.5/forecast";

    public WeatherCurrent getCurrentWeatherByName(String name){
        try {
            HttpResponse<String> response = getCurrentResponseByName(name);
            String jsonAnswer = response.body();
            ObjectMapper objectMapper = new ObjectMapper();
            return WeatherService.toWeatherCurrentFromWeatherDataCurrent(objectMapper.readValue(jsonAnswer, WeatherDataCurrent.class));
        }
        catch (Exception exception){
            exception.printStackTrace();
        }
        return null;
    }

    public WeatherCurrent getCurrentWeatherByCoordinates(Coord coord) {
        try {
            HttpResponse<String> response = getResponseByCoord(coord, API_URI_CURRENT);
            String jsonAnswer = response.body();
            ObjectMapper objectMapper = new ObjectMapper();
            return WeatherService.toWeatherCurrentFromWeatherDataCurrent(objectMapper.readValue(jsonAnswer, WeatherDataCurrent.class));
        }
        catch (Exception exception){
            exception.printStackTrace();
        }
        return null;
    }

    public List<WeatherCurrent> getCurrentWeathersByLocations(List<Location> locationList){
        try {
            List<WeatherCurrent> weathesList = new ArrayList<>();
            for (Location location : locationList){
                Coord coord = new Coord();
                coord.setLat(location.getLatitude());
                coord.setLon(location.getLongitude());
                WeatherCurrent weatherCurrent = getCurrentWeatherByCoordinates(coord);
                weatherCurrent.setCoord(coord);
                weathesList.add(weatherCurrent);
            }
            return weathesList;
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    public List<WeatherDaily> getWeatherForecast(Coord coord) {
        try {
            HttpResponse<String> response = getResponseByCoord(coord, API_URI_FORECAST);
            String jsonAnswer = response.body();
            ObjectMapper objectMapper = new ObjectMapper();
            WeatherDataForecast weatherDataForecast = objectMapper.readValue(jsonAnswer, WeatherDataForecast.class);
            return WeatherService.toWeatherDailyFromWeatherDataForecast(weatherDataForecast);
        }
        catch (Exception exception){
            exception.printStackTrace();
        }
        return null;
    }

    private HttpResponse<String> getCurrentResponseByName(String name) throws URISyntaxException, IOException, InterruptedException {
        String requestURI = String.format("%s?q=%s&appid=%s", API_URI_CURRENT, name, API_KEY);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(requestURI))
                .GET()
                .build();

        HttpClient client = HttpClient.newHttpClient();
        return client.send(request, HttpResponse.BodyHandlers.ofString());
    }

    private HttpResponse<String> getResponseByCoord(Coord coord, String API_URI) throws URISyntaxException, IOException, InterruptedException {
        double latitude = coord.getLat();
        double longitude = coord.getLon();

        String requestURI = String.format("%s?lat=%s&lon=%s&appid=%s", API_URI, latitude, longitude, API_KEY);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(requestURI))
                .GET()
                .build();

        HttpClient client = HttpClient.newHttpClient();
        return client.send(request, HttpResponse.BodyHandlers.ofString());
    }
}
