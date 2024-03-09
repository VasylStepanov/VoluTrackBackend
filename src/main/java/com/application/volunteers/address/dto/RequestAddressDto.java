package com.application.volunteers.address.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
public record RequestAddressDto(String region,
                                String settlement,
                                String location,
                                Double coordinatesLatitude,
                                Double coordinatesLongitude) {
}
