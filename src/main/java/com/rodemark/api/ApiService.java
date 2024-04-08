package com.rodemark.api;

import com.rodemark.api.models.Coordinates;
import com.rodemark.api.models.Weather;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ApiService {
    private final String API_KEY = "d85e195b5942ef6461aa1a292de5eae6";
    private final String API_URI = "https://api.openweathermap.org/data/2.5/weather";

    public Coordinates getCoordinatesByName(String name) throws URISyntaxException, IOException, InterruptedException {
        String requestURI = String.format("%s?q=%s&appid=%s", API_URI, name, API_KEY);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(requestURI))
                .GET()
                .build();

        HttpClient client = HttpClient.newHttpClient();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 200) {
            return parseCoordinatesFromResponse(response);
        } else {
            throw new IOException("Error fetching coordinates: " + response.statusCode());
        }
    }

    public Weather getWeatherByCoordinates(Coordinates coordinates) throws IOException, InterruptedException, URISyntaxException {
        double latitude = coordinates.getLatitude();
        double longitude = coordinates.getLongitude();

        String requestURI = String.format("%s?lat=%s&lon=%s&appid%s", API_URI, latitude, longitude, API_KEY);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(requestURI))
                .GET()
                .build();

        HttpClient client = HttpClient.newHttpClient();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 200) {
            return parseInformationAboutWeather(response);
        } else {
            throw new IOException("Error fetching coordinates: " + response.statusCode());
        }
    }

    private Coordinates parseCoordinatesFromResponse(HttpResponse<String> response){
        Coordinates coordinates = new Coordinates();

        JSONObject jsonpObject = new JSONObject(response.body());
        JSONObject jsonCoordinates = jsonpObject.getJSONObject("cord");
        double latitude = jsonCoordinates.getDouble("lat");
        double longitude = jsonCoordinates.getDouble("lon");

        coordinates.setLatitude(latitude);
        coordinates.setLongitude(longitude);

        return coordinates;
    }

    private Weather parseInformationAboutWeather(HttpResponse<String> response){
        Weather weather = new Weather();
        weather.setCoordinates(parseCoordinatesFromResponse(response));
        JSONObject jsonpObject = new JSONObject(response.body());

        JSONObject jsonObjectSecond = jsonpObject.getJSONObject("main");

        double temp = jsonObjectSecond.getDouble("temp");
        double pressure = jsonObjectSecond.getDouble("pressure");
        double humidity = jsonObjectSecond.getDouble("humidity");

        jsonObjectSecond = jsonpObject.getJSONObject("weather");

        String description = jsonObjectSecond.getString("description");

        jsonObjectSecond = jsonpObject.getJSONObject("wind");
        double windSpeed = jsonObjectSecond.getDouble("speed");
        double windDirection = jsonObjectSecond.getDouble("deg");

        jsonObjectSecond = jsonpObject.getJSONObject("sys");

        String country = jsonObjectSecond.getString("country");

        String nameOfCity = jsonpObject.getString("name");

        weather.setTemp(temp);
        weather.setPressure(pressure);
        weather.setHumidity(humidity);
        weather.setDescription(description);
        weather.setWindSpeed(windSpeed);
        weather.setWindDirection(windDirection);
        weather.setCountry(country);
        weather.setNameOfCity(nameOfCity);

        return weather;
    }

}
