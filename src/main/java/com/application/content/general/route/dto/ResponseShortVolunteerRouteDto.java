package com.application.content.general.route.dto;

import com.application.content.general.route.model.Route;
import com.application.content.general.route.model.RouteStatus;
import com.application.content.items.inventory.dto.ResponseShortInventoryItemDto;
import com.application.content.volunteers.volunteer.dto.ResponseDriverDto;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ResponseShortVolunteerRouteDto {

    LocalDateTime startAt;

    RouteStatus routeStatus;

    ResponseDriverDto responseDriverDto;

    ResponseShortInventoryItemDto responseShortInventoryItemDto;

    public static ResponseShortVolunteerRouteDto toResponseShortRouteDto(Route route) {
        return new ResponseShortVolunteerRouteDto(
                route.getStartAt(),
                route.getRouteStatus(),
                ResponseDriverDto.toResponseDriverDto(route.getDriver(), route.getCar()),
                ResponseShortInventoryItemDto.toResponseShortInventoryItemDto(route.getInventoryItem())
        );
    }
}