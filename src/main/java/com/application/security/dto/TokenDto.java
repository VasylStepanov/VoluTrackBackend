package com.application.security.dto;

import com.application.security.token.Token;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TokenDto {

    UUID id;

    String refreshToken;

    LocalDateTime createdAt;

    LocalDateTime updatedAt;

    public static TokenDto toTokenDto(Token token){
        return new TokenDto(token.getId(),
                token.getRefreshToken(),
                token.getCreatedAt(),
                token.getUpdatedAt());
    }
}
