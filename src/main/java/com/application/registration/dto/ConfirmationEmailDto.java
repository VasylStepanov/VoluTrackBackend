package com.application.registration.dto;

import com.application.registration.model.ConfirmationEmail;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ConfirmationEmailDto {

    UUID id;

    boolean confirmed;

    String token;

    LocalDateTime createdAt;

    LocalDateTime updatedAt;

    LocalDateTime expiresAt;

    public static ConfirmationEmailDto toConfirmationEmailDto(ConfirmationEmail confirmationEmail) {
        return new ConfirmationEmailDto(confirmationEmail.getId(),
                confirmationEmail.isConfirmed(),
                confirmationEmail.getToken(),
                confirmationEmail.getCreatedAt(),
                confirmationEmail.getUpdatedAt(),
                confirmationEmail.getExpiresAt());
    }
}
