package com.application.registration.dto.request;

import com.application.user.model.UserRole;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
public record RegistrationRequest( String fullName, String email, String password, UserRole role) {
}
