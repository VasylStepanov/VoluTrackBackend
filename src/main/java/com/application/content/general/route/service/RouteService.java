package com.application.content.general.route.service;

import com.application.content.general.route.dto.RequestRouteDto;
import com.application.content.general.route.dto.RequestUpdateRouteDto;
import com.application.content.general.route.model.Route;
import com.application.content.items.inventory.model.InventoryItem;
import com.application.content.items.request.model.RequestItem;
import org.springframework.scheduling.annotation.Async;

import java.util.List;
import java.util.UUID;

public interface RouteService {
    Route getRoute(UUID routeId);

    Route getRouteEitherDriver(UUID driverId, UUID routeId);

    boolean isCarInRoute(UUID carId);

    boolean isInventoryItemInRoute(UUID inventoryItemId);

    boolean isVolunteerInRoute(UUID volunteerId);

    List<Route> getVolunteerRoutes(UUID volunteerId);

    List<Route> getGroupRoutes(UUID volunteerId, UUID groupId);

    List<Route> getDriverRoutes(UUID volunteerId);

    void createRoute(UUID volunteerId, RequestRouteDto requestRouteDto);

    void updateRoute(UUID volunteerId, RequestUpdateRouteDto requestUpdateRouteDto);

    void deleteRoute(UUID volunteerId, UUID routeId);

    void setItemToRouteByInventoryItem(InventoryItem inventoryItem);

    @Async
    void setItemToRouteByRequestItem(RequestItem requestItem);
}
