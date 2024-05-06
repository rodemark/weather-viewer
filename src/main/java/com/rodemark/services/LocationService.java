package com.rodemark.services;

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

    public List<Location> findAll(User user){
        return locationRepository.findByUser(user);
    }

    public Location findByUserAndLongitudeAndLatitudeAndName(User user, double longitude, double latitude, String name) {
        return locationRepository.findByUserAndLongitudeAndLatitudeAndName(user, longitude, latitude, name);
    }

    @Transactional
    public void saveLocation(Location location){
        locationRepository.save(location);
    }

    @Transactional
    public void deleteLocation(Location location) {
        locationRepository.delete(location);
    }

    @Transactional
    public void deleteByUserAndLongitudeAndLatitudeAndName(User user, double longitude, double latitude, String name) {
        locationRepository.deleteByUserAndLongitudeAndLatitudeAndName(user, longitude, latitude, name);
    }

}
