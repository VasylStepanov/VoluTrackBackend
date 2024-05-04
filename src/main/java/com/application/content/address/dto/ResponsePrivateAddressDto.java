package com.application.content.address.dto;


import com.application.content.address.model.Address;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ResponsePrivateAddressDto {

    String region;

    String settlement;

    String location;

    Double coordinatesLatitude;

    Double coordinatesLongitude;

    public static ResponsePrivateAddressDto toResponseAddressDto(Address address){
        if(address == null)
            return null;
        return new ResponsePrivateAddressDto(
                address.getRegion(),
                address.getSettlement(),
                address.getLocation(),
                address.getCoordinatesLatitude(),
                address.getCoordinatesLatitude());
    }
}
