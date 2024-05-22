package com.application.content.general.route.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record RequestUpdateRouteDto(UUID routeId, UUID carId, LocalDateTime startAt) {
}
