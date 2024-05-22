package com.application.content.general.route.controller;

import com.application.content.general.address.model.Address;
import com.application.content.general.address.service.AddressService;
import com.application.content.general.route.dto.*;
import com.application.content.general.route.model.Route;
import com.application.content.general.route.service.RouteService;
import com.application.content.volunteers.volunteer.service.VolunteerService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/route")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RouteController {

    @Autowired
    VolunteerService volunteerService;

    @Autowired
    RouteService routeService;

    @Autowired
    AddressService addressService;

    @Operation(summary = "Get volunteer rotes",
            description = "Volunteer gets a list of items what he/she should give to driver or receive fro driver an item.")
    @GetMapping("/getAllByVolunteerId")
    public ResponseEntity<?> getVolunteerRoutes(HttpServletRequest httpServletRequest){
        UUID volunteerId = volunteerService.getVolunteerId(httpServletRequest);
        List<Route> routeList = routeService.getVolunteerRoutes(volunteerId);
        return ResponseEntity.ok(routeList.stream().map(ResponseShortVolunteerRouteDto::toResponseShortRouteDto));
    }

    @Operation(summary = "Get group rotes",
            description = """
                Group gets a list of items what he/she should give to driver or receive fro driver.
                Moderator or admin should give to driver or receive fro driver an item.
                """)
    @GetMapping("/getAllByGroupId")
    public ResponseEntity<?> getGroupRoutes(HttpServletRequest httpServletRequest,
                                               @RequestParam UUID groupId){
        UUID volunteerId = volunteerService.getVolunteerId(httpServletRequest);
        List<Route> routeList = routeService.getGroupRoutes(volunteerId, groupId);
        return ResponseEntity.ok(routeList.stream().map(ResponseShortVolunteerRouteDto::toResponseShortRouteDto));
    }

    @Operation(summary = "Get driver rotes", description = """
                Driver gets a list of his routes, and item where he needs to take and address to where he needs to deliver.
                """)
    @GetMapping("/getAllByDriverId")
    public ResponseEntity<?> getDriverRoutes(HttpServletRequest httpServletRequest){
        UUID volunteerId = volunteerService.getVolunteerId(httpServletRequest);
        List<Route> routeList = routeService.getDriverRoutes(volunteerId);
        return ResponseEntity.ok(routeList.stream().map(ResponseShortDriverRouteDto::toResponseShortDriverRouteDto));
    }

    @Operation(summary = "Get driver rote by routeId", description = """
                Driver gets a route full info of his routes, and item where he needs to take and address to where he needs to deliver.
                """)
    @GetMapping("/getDriverRouteById")
    public ResponseEntity<?> getDriverRouteById(HttpServletRequest httpServletRequest,
                                                @RequestParam UUID routeId){
        UUID volunteerId = volunteerService.getVolunteerId(httpServletRequest);
        Route route = routeService.getRouteEitherDriver(volunteerId, routeId);
        Address from = null, to = null;
        if(route.getInventoryItem() != null || route.getRequestItem() != null) {
            from = addressService.getAddress(route.getInventoryItem());
            to = addressService.getAddress(route.getRequestItem());
        }
        return ResponseEntity.ok(ResponseFullDriverRouteDto.toResponseShortDriverRouteDto(route, from, to));
    }

    @PostMapping
    public ResponseEntity<?> createRoute(HttpServletRequest httpServletRequest,
                                         @RequestBody RequestRouteDto requestRouteDto){
        UUID volunteerId = volunteerService.getVolunteerId(httpServletRequest);
        routeService.createRoute(volunteerId, requestRouteDto);
        return ResponseEntity.ok("Route is set");
    }

    @PutMapping
    public ResponseEntity<?> updateRoute(HttpServletRequest httpServletRequest,
                                         @RequestBody RequestUpdateRouteDto requestUpdateRouteDto){
        UUID volunteerId = volunteerService.getVolunteerId(httpServletRequest);
        routeService.updateRoute(volunteerId, requestUpdateRouteDto);
        return ResponseEntity.ok("Route is updated");
    }

    @DeleteMapping
    public ResponseEntity<?> deleteRoute(HttpServletRequest httpServletRequest,
                                         @RequestParam UUID routeId){
        UUID volunteerId = volunteerService.getVolunteerId(httpServletRequest);
        routeService.deleteRoute(volunteerId, routeId);
        return ResponseEntity.ok("Route is deleted");
    }
}
