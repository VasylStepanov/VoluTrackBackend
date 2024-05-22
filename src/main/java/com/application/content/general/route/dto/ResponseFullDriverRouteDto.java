package com.application.content.general.route.dto;

import com.application.content.general.address.dto.ResponsePrivateAddressDto;
import com.application.content.general.address.model.Address;
import com.application.content.general.route.model.Route;
import com.application.content.general.route.model.RouteStatus;
import com.application.content.items.inventory.dto.ResponseShortInventoryItemDto;
import com.application.content.volunteers.volunteer.dto.VolunteerPrivateProfileDto;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ResponseFullDriverRouteDto {

    LocalDateTime startAt;

    RouteStatus routeStatus;

    ResponseShortInventoryItemDto responseShortInventoryItemDto;

    ResponsePrivateAddressDto from;

    ResponsePrivateAddressDto to;

    ResponsePrivateAddressDto fromItem;

    ResponsePrivateAddressDto toRequest;

    VolunteerPrivateProfileDto fromVolunteer;

    VolunteerPrivateProfileDto toVolunteer;

    public static ResponseFullDriverRouteDto toResponseShortDriverRouteDto(Route route, Address from, Address to) {
        return new ResponseFullDriverRouteDto(route.getStartAt(),
                route.getRouteStatus(),
                ResponseShortInventoryItemDto.toResponseShortInventoryItemDto(route.getInventoryItem()),
                ResponsePrivateAddressDto.toResponseAddressDto(route.getFromAddress()),
                ResponsePrivateAddressDto.toResponseAddressDto(route.getToAddress()),
                ResponsePrivateAddressDto.toResponseAddressDto(from),
                ResponsePrivateAddressDto.toResponseAddressDto(to),
                VolunteerPrivateProfileDto.toVolunteerPrivateProfileDto(route.getVolunteerGiver()),
                VolunteerPrivateProfileDto.toVolunteerPrivateProfileDto(route.getVolunteerTaker()));
    }
}
