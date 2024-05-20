package com.application.content.address.dto;


import com.application.content.address.model.Address;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ResponsePrivateAddressDto {

    String address;

    Double coordinatesLatitude;

    Double coordinatesLongitude;

    public static ResponsePrivateAddressDto toResponseAddressDto(Address address){
        if(address == null)
            return null;
        return new ResponsePrivateAddressDto(
                address.getAddress(),
                address.getCoordinatesLatitude(),
                address.getCoordinatesLatitude());
    }
}
