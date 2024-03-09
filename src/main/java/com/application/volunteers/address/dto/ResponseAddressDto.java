package com.application.volunteers.address.dto;


import com.application.volunteers.address.model.Address;
import com.application.volunteers.item.dto.ResponseItemDto;
import com.application.volunteers.item.model.Item;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class ResponseAddressDto {

    String region;

    String settlement;

    String location;

    Double coordinatesLatitude;

    Double coordinatesLongitude;

    public static ResponseAddressDto toResponseAddressDto(Address address){
        return new ResponseAddressDto(
                address.getRegion(),
                address.getSettlement(),
                address.getLocation(),
                address.getCoordinatesLatitude(),
                address.getCoordinatesLatitude());
    }
}
