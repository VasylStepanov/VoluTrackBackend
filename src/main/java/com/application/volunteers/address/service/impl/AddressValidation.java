package com.application.volunteers.address.service.impl;

import org.springframework.stereotype.Service;

@Service
public class AddressValidation {

    public String eitherRegionIsValid(String region){
        if(region.length() < 2 || region.length() >= 32)
            throw new RuntimeException("Region is invalid");
        return region;
    }

    public String eitherSettlementIsValid(String settlement) {
        if(settlement.length() < 2 || settlement.length() >= 64 )
            throw new RuntimeException("Settlement is invalid");
        return settlement;
    }

    public String eitherLocationIsValid(String location) {
        if(location.length() < 2 || location.length() >= 128)
            throw new RuntimeException("Location is invalid");
        return location;
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

    public String eitherRegionIsValidFull(String region) {
        if(region == null)
            throw new RuntimeException("Region is null");
        return eitherRegionIsValid(region);
    }

    public String eitherSettlementIsValidFull(String settlement) {
        if(settlement == null)
            throw new RuntimeException("Settlement is null");
        return eitherSettlementIsValid(settlement);
    }

    public String eitherLocationIsValidFull(String location) {
        if(location == null)
            throw new RuntimeException("Location is null");
        return eitherLocationIsValid(location);
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
