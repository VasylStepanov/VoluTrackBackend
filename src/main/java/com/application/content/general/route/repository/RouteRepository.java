package com.application.content.general.route.repository;

import com.application.content.general.route.model.Route;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface RouteRepository extends JpaRepository<Route, UUID> {

    List<Route> findRoutesByInventoryItemIdIn(List<UUID> inventoryItemIdList);

    List<Route> findRoutesByRequestItemIdIn(List<UUID> requestItemIdList);

    List<Route> findRoutesByDriverId(UUID driverId);

    List<Route> findByCarId(UUID carId);

    List<Route> findByInventoryItemId(UUID inventoryItemId);

    List<Route> findByRequestItemId(UUID requestItemId);

    List<Route> findByVolunteerGiverId(UUID volunteerGiverId);

    List<Route> findByVolunteerTakerId(UUID volunteerTakerId);

    @Query("""
            SELECT r FROM route r WHERE r.fromAddress.coordinatesLongitude < ?1
            AND r.fromAddress.coordinatesLongitude > ?2
            AND r.fromAddress.coordinatesLatitude < ?3
            AND r.fromAddress.coordinatesLatitude > ?4
            AND r.routeStatus = 'CREATED'""")
    List<Route> findRouteByAddressFrom(double longitudeX,
                                       double longitudeY,
                                       double latitudeX,
                                       double latitudeY);

    @Query("""
            SELECT r FROM route r WHERE r.toAddress.coordinatesLongitude < ?1
            AND r.toAddress.coordinatesLongitude > ?2
            AND r.toAddress.coordinatesLatitude < ?3
            AND r.toAddress.coordinatesLatitude > ?4
            AND r.routeStatus = 'CREATED'""")
    List<Route> findRouteByAddressTo(double longitudeX,
                                     double longitudeY,
                                     double latitudeX,
                                     double latitudeY);

}
