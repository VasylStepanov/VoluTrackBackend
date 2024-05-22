package com.application.content.general.route.service;

import com.application.content.general.route.dto.RequestRouteDto;
import com.application.content.general.route.dto.RequestUpdateRouteDto;
import com.application.content.general.route.model.Route;

import java.util.List;
import java.util.UUID;

public interface RouteService {
    Route getRoute(UUID routeId);

    Route getRouteEitherDriver(UUID driverId, UUID routeId);

    List<Route> getVolunteerRoutes(UUID volunteerId);

    List<Route> getGroupRoutes(UUID volunteerId, UUID groupId);

    List<Route> getDriverRoutes(UUID volunteerId);

    void createRoute(UUID volunteerId, RequestRouteDto requestRouteDto);

    void updateRoute(UUID volunteerId, RequestUpdateRouteDto requestUpdateRouteDto);

    void deleteRoute(UUID volunteerId, UUID routeId);
}
