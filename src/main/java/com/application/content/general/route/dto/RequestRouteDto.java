package com.application.content.general.route.dto;

import com.application.content.general.address.dto.RequestAddressDto;

import java.time.LocalDateTime;
import java.util.UUID;

public record RequestRouteDto(RequestAddressDto from, RequestAddressDto to, UUID carId, LocalDateTime startAt) {
}
