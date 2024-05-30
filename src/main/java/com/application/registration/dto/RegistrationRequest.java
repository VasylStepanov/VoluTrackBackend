package com.application.registration.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
public record RegistrationRequest( String firstName,
                                   String lastName,
                                   String email,
                                   String phoneNumber,
                                   String password) {
}
