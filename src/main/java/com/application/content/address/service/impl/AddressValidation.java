package com.application.content.address.service.impl;

import org.springframework.stereotype.Service;

@Service
public class AddressValidation {

    public String eitherAddressIsValid(String address){
        if(address.length() < 2 || address.length() > 256)
            throw new RuntimeException("Address is invalid");
        return address;
    }


    public Double eitherCoordinatesLatitude(Double coordinatesLatitude) {
        if(coordinatesLatitude.isNaN())
            throw new RuntimeException("Latitude is NaN");
        return coordinatesLatitude;
    }

    public Double eitherCoordinatesLongitude(Double coordinatesLongitude) {
        if(coordinatesLongitude.isNaN())
            throw new RuntimeException("Longitude is NaN");
        return coordinatesLongitude;
    }

    public String eitherAddressIsValidFull(String address) {
        if(address == null)
            throw new RuntimeException("Address is null");
        return eitherAddressIsValid(address);
    }


    public Double eitherCoordinatesLatitudeFull(Double coordinatesLatitude) {
        if(coordinatesLatitude == null)
            return null;
        return eitherCoordinatesLatitude(coordinatesLatitude);
    }

    public Double eitherCoordinatesLongitudeFull(Double coordinatesLongitude) {
        if(coordinatesLongitude == null)
            return null;
        return eitherCoordinatesLongitude(coordinatesLongitude);
    }
}
