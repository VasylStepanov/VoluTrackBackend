package com.application.volunteers.address.dto;

import com.application.volunteers.address.model.Address;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.UUID;


@Getter
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AddressDto {

    UUID id;

    String region;

    String settlement;

    String location;

    Double coordinatesLatitude;

    Double coordinatesLongitude;

    LocalDateTime createdAt;

    LocalDateTime updatedAt;

    UUID volunteerId;

    public static AddressDto toAddressDto(Address address){
        return new AddressDto(address.getId(),
                address.getRegion(),
                address.getSettlement(),
                address.getLocation(),
                address.getCoordinatesLatitude(),
                address.getCoordinatesLongitude(),
                address.getCreatedAt(),
                address.getUpdatedAt(),
                address.getVolunteer().getId());
    }
}
