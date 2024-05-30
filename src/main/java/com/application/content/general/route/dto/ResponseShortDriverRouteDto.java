package com.application.content.general.route.dto;

import com.application.content.general.address.dto.ResponsePrivateAddressDto;
import com.application.content.general.route.model.Route;
import com.application.content.items.inventory.dto.ResponseShortInventoryItemDto;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ResponseShortDriverRouteDto {

    UUID routeId;

    LocalDateTime startAt;

    ResponsePrivateAddressDto from;

    ResponsePrivateAddressDto to;

    ResponseShortInventoryItemDto responseShortInventoryItemDto;

    public static ResponseShortDriverRouteDto toResponseShortDriverRouteDto(Route route) {
        return new ResponseShortDriverRouteDto(route.getId(),
                route.getStartAt(),
                ResponsePrivateAddressDto.toResponseAddressDto(route.getFromAddress()),
                ResponsePrivateAddressDto.toResponseAddressDto(route.getToAddress()),
                ResponseShortInventoryItemDto.toResponseShortInventoryItemDto(route.getInventoryItem()));
    }

}
