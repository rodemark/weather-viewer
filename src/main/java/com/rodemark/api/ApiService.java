package com.rodemark.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rodemark.api.currentWeather.Coord;
import com.rodemark.api.currentWeather.WeatherData;
import com.rodemark.api.others.WeatherRedesigned;
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
    private final String API_URI = "https://api.openweathermap.org/data/2.5/weather";

    public WeatherRedesigned getWeatherByName(String name){
        try {
            HttpResponse<String> response = getResponseByName(name);
            String jsonAnswer = response.body();
            ObjectMapper objectMapper = new ObjectMapper();
            return WeatherService.weatherTransfer(objectMapper.readValue(jsonAnswer, WeatherData.class));
        }
        catch (Exception exception){
            exception.printStackTrace();
        }
        return null;
    }

    public WeatherRedesigned getWeatherByCoordinates(Coord coord) {
        try {
            HttpResponse<String> response = getResponseByCoord(coord);
            String jsonAnswer = response.body();
            ObjectMapper objectMapper = new ObjectMapper();
            return WeatherService.weatherTransfer(objectMapper.readValue(jsonAnswer, WeatherData.class));
        }
        catch (Exception exception){
            exception.printStackTrace();
        }
        return null;
    }

    public List<WeatherRedesigned> getWeathersByLocations(List<Location> locationList){
        try {
            List<WeatherRedesigned> weathesList = new ArrayList<>();
            for (Location location : locationList){
                Coord coord = new Coord();
                coord.setLat(location.getLatitude());
                coord.setLon(location.getLongitude());
                WeatherRedesigned weatherRedesigned = getWeatherByCoordinates(coord);
                weatherRedesigned.setCoord(coord);
                weathesList.add(weatherRedesigned);
            }
            return weathesList;
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    private HttpResponse<String> getResponseByName(String name) throws URISyntaxException, IOException, InterruptedException {
        String requestURI = String.format("%s?q=%s&appid=%s", API_URI, name, API_KEY);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(requestURI))
                .GET()
                .build();

        HttpClient client = HttpClient.newHttpClient();
        return client.send(request, HttpResponse.BodyHandlers.ofString());
    }

    private HttpResponse<String> getResponseByCoord(Coord coord) throws URISyntaxException, IOException, InterruptedException {
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
