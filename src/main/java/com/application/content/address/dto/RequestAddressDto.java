package com.application.content.address.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
public record RequestAddressDto(String address,
                                Double coordinatesLatitude,
                                Double coordinatesLongitude) {
}
