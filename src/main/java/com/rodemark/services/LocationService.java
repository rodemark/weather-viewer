package com.rodemark.services;

import com.rodemark.api.models.WeatherFromApi;
import com.rodemark.models.Location;
import com.rodemark.models.User;
import com.rodemark.repositories.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class LocationService {
    private final LocationRepository locationRepository;

    @Autowired
    public LocationService(LocationRepository locationRepository){
        this.locationRepository = locationRepository;
    }

    @Transactional
    public void saveLocation(WeatherFromApi weatherFromApi, User user){
        Location location = new Location();
        location.setName(weatherFromApi.getNameOfCity());
        location.setLongitude(weatherFromApi.getCoordinates().getLongitude());
        location.setLatitude(weatherFromApi.getCoordinates().getLatitude());
        location.setUser(user);

        locationRepository.save(location);
    }

    @Transactional
    public void deleteLocation(WeatherFromApi weatherFromApi, User user){
        double longitude = weatherFromApi.getCoordinates().getLongitude();
        double latitude = weatherFromApi.getCoordinates().getLatitude();
        String name = weatherFromApi.getNameOfCity();

        System.out.println("Attempting to delete location: " + name + ", Latitude: " + latitude + ", Longitude: " + longitude);

        // Проверяем, существует ли местоположение перед удалением
        List<Location> locationsToDelete = locationRepository.findByUserAndLongitudeAndLatitudeAndName(user, longitude, latitude, name);
        if(locationsToDelete != null && !locationsToDelete.isEmpty()) {
            locationRepository.deleteByUserAndLongitudeAndLatitudeAndName(user, longitude, latitude, name);
            System.out.println("Deleted location: " + name + ", Latitude: " + latitude + ", Longitude: " + longitude);
        } else {
            System.out.println("Location not found for deletion");
        }
    }


    public List<Location> getLocations(User user){
        return locationRepository.findByUser(user);
    }

}
