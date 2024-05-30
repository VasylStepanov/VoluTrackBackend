package com.application.content.general.address.dto;


import com.application.content.general.address.model.Address;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ResponsePrivateAddressDto {

    String address;

    Double coordinatesLongitude;

    Double coordinatesLatitude;

    public static ResponsePrivateAddressDto toResponseAddressDto(Address address){
        if(address == null)
            return null;
        return new ResponsePrivateAddressDto(
                address.getAddress(),
                address.getCoordinatesLongitude(),
                address.getCoordinatesLatitude());
    }
}
