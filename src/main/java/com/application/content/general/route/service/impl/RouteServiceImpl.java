package com.application.content.general.route.service.impl;

import com.application.config.BaseEntity;
import com.application.content.general.address.model.Address;
import com.application.content.general.address.service.AddressService;
import com.application.content.general.route.dto.RequestRouteDto;
import com.application.content.general.route.dto.RequestUpdateRouteDto;
import com.application.content.general.route.model.Route;
import com.application.content.general.route.model.RouteStatus;
import com.application.content.general.route.repository.RouteRepository;
import com.application.content.general.route.service.RouteService;
import com.application.content.groups.group.model.Group;
import com.application.content.groups.group.service.GroupService;
import com.application.content.items.inventory.model.Inventory;
import com.application.content.items.inventory.model.InventoryItem;
import com.application.content.items.inventory.service.InventoryService;
import com.application.content.items.request.dto.RequestStatus;
import com.application.content.items.request.model.RequestItem;
import com.application.content.items.request.service.RequestService;
import com.application.content.volunteers.car.model.Car;
import com.application.content.volunteers.car.service.CarService;
import com.application.content.volunteers.volunteer.model.Volunteer;
import com.application.content.volunteers.volunteer.service.VolunteerService;
import lombok.AccessLevel;
import lombok.Setter;
import lombok.SneakyThrows;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

@Service
@Transactional
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RouteServiceImpl implements RouteService {

    @Setter
    @Value("${const.size_length}")
    double SIZE_LENGTH;

    @Autowired
    RouteRepository routeRepository;

    @Autowired
    VolunteerService volunteerService;

    @Autowired
    GroupService groupService;

    @Autowired
    AddressService addressService;

    @Autowired
    CarService carService;

    @Autowired
    RequestService requestService;

    @Autowired
    InventoryService inventoryService;

    @Override
    public Route getRoute(UUID routeId) {
        Route route = routeRepository.findById(routeId)
                .orElseThrow(() -> new RuntimeException("Route not found"));

        if(route.getRouteStatus() == RouteStatus.DRIVER_TAKE ||
                route.getRouteStatus() == RouteStatus.DRIVER_GIVE)
            throw new RuntimeException("Can't modify route");
        return route;
    }

    @Override
    public Route getRouteEitherDriver(UUID driverId, UUID routeId) {
        Route route = getRoute(routeId);

        if(!route.getDriver().getId().equals(driverId))
            throw new RuntimeException("Volunteer isn't owner of the route");

        return route;
    }

    @Override
    public boolean isCarInRoute(UUID carId) {
        List<Route> route = routeRepository.findByCarId(carId);
        return route != null && !route.isEmpty();
    }

    @Override
    public boolean isInventoryItemInRoute(UUID inventoryItemId) {
        List<Route> route = routeRepository.findByInventoryItemId(inventoryItemId);
        return route != null && !route.isEmpty();
    }

    @Override
    public boolean isVolunteerInRoute(UUID volunteerId) {
        List<Route> routes = routeRepository.findByVolunteerGiverId(volunteerId);
        if(routes != null && !routes.isEmpty())
            return true;
        routes = routeRepository.findByVolunteerTakerId(volunteerId);
        return routes != null && !routes.isEmpty();
    }

    @Override
    public List<Route> getVolunteerRoutes(UUID volunteerId){
        Volunteer volunteer = volunteerService.getVolunteer(volunteerId);

        List<UUID> inventoryItemIds = volunteer.getInventory()
                .getInventoryItems()
                .stream()
                .map(BaseEntity::getId)
                .toList();

        List<UUID> requestItemIds = volunteer.getRequest()
                .getRequestItems()
                .stream()
                .map(BaseEntity::getId)
                .toList();

        return Stream.concat(routeRepository.findRoutesByInventoryItemIdIn(inventoryItemIds).stream(),
                routeRepository.findRoutesByRequestItemIdIn(requestItemIds).stream()).toList();
    }

    @Override
    public List<Route> getGroupRoutes(UUID volunteerId, UUID groupId){
        Group group = groupService.eitherIsAGroupModerator(volunteerId, groupId);

        List<UUID> inventoryItemIds = group.getInventory()
                .getInventoryItems()
                .stream()
                .map(BaseEntity::getId)
                .toList();
        List<UUID> requestItemIds = group.getRequest()
                .getRequestItems()
                .stream()
                .map(BaseEntity::getId)
                .toList();

        return Stream.concat(routeRepository.findRoutesByInventoryItemIdIn(inventoryItemIds).stream(),
                routeRepository.findRoutesByRequestItemIdIn(requestItemIds).stream()).toList();
    }

    @Override
    public List<Route> getDriverRoutes(UUID volunteerId) {
        return routeRepository.findRoutesByDriverId(volunteerId);
    }

    @Override
    public void createRoute(UUID volunteerId, RequestRouteDto requestRouteDto) {
        Volunteer volunteer = volunteerService.getVolunteer(volunteerId);
        Address from = addressService.saveAddress(requestRouteDto.from());
        Address to = addressService.saveAddress(requestRouteDto.to());

        Car car = carService.eitherIsCarOwner(volunteerId, requestRouteDto.carId());
        if(car == null)
            car = volunteer.getCarList().stream()
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("Driver don't have a car"));

        Route route = Route.builder()
                .startAt(requestRouteDto.startAt())
                .routeStatus(RouteStatus.CREATED)
                .driver(volunteer)
                .car(car)
                .fromAddress(from)
                .toAddress(to)
                .build();
        route = routeRepository.save(route);
        setItemToRoute(route);
    }

    @Override
    public void setRouteStatusGiven(UUID volunteerId, UUID routeId) {
        Route route = routeRepository
                .findById(routeId)
                .orElseThrow(() -> new RuntimeException("There is no such a route"));
        if(route.getVolunteerGiver().getId().equals(volunteerId))
            route.setRouteStatus(RouteStatus.DRIVER_TAKE);
    }

    @Override
    public void transferItem(UUID volunteerId, UUID routeId) {
        Route route = routeRepository
                .findById(routeId)
                .orElseThrow(() -> new RuntimeException("There is no such a route"));

        Inventory inventory = inventoryService.getInventoryByRequest(route.getRequestItem());
        route.getInventoryItem().setInventory(inventory);
        deleteRoute(route);
    }

    @Override
    @SneakyThrows
    public void updateRoute(UUID volunteerId, RequestUpdateRouteDto requestUpdateRouteDto) {
        Route route = getRouteEitherDriver(volunteerId, requestUpdateRouteDto.routeId());

        if(requestUpdateRouteDto.startAt() != null
                && LocalDateTime.now().isBefore(requestUpdateRouteDto.startAt()))
            route.setStartAt(requestUpdateRouteDto.startAt());

        if(requestUpdateRouteDto.carId() != null) {
            Car car = carService.eitherIsCarOwner(volunteerId, requestUpdateRouteDto.carId());
            route.setCar(car);
        }
    }

    @Override
    public void deleteRoute(UUID volunteerId, UUID routeId) {
        Route route = getRouteEitherDriver(volunteerId, routeId);
        routeRepository.delete(route);
    }

    private void deleteRoute(Route route) {
        routeRepository.delete(route);
    }

    @Async
    @Override
    public void setItemToRouteByInventoryItem(InventoryItem inventoryItem){
        Address address = addressService.getAddress(inventoryItem);
        Volunteer giverRepresentative = inventoryService.getRepresentative(inventoryItem);
        List<Route> routeList = routeRepository.findRouteByAddressFrom(
                address.getCoordinatesLongitude() + SIZE_LENGTH,
                address.getCoordinatesLongitude() - SIZE_LENGTH,
                address.getCoordinatesLatitude() + SIZE_LENGTH,
                address.getCoordinatesLatitude() - SIZE_LENGTH);

        if(routeList != null && !routeList.isEmpty()) {
            for(Route route: routeList){
                List<RequestItem> requestList = requestService.findAllRequestItemsByAddressAndItemType(
                        route.getToAddress(), inventoryItem.getItemType());
                if(requestList != null && !requestList.isEmpty()){
                    RequestItem requestItem = getClosestRequestItem(requestList, route.getToAddress());
                    if(!requestItem.getRequestStatus().equals(RequestStatus.IN_PROCESS))
                        requestItem.setRequestStatus(RequestStatus.IN_PROCESS);
                    Volunteer takerRepresentative = requestService.getRepresentative(requestItem);
                    route.setInventoryItem(inventoryItem);
                    route.setRequestItem(requestItem);
                    route.setVolunteerGiver(giverRepresentative);
                    route.setVolunteerTaker(takerRepresentative);
                    route.setRouteStatus(RouteStatus.SET);
                    inventoryItem.setReadyToSend(false);
                    return;
                }
            }
        }
    }

    @Async
    @Override
    public void setItemToRouteByRequestItem(RequestItem requestItem){
        Address address = addressService.getAddress(requestItem);
        Volunteer takerRepresentative = requestService.getRepresentative(requestItem);
        List<Route> routeList = routeRepository.findRouteByAddressTo(
                address.getCoordinatesLongitude() + SIZE_LENGTH,
                address.getCoordinatesLongitude() - SIZE_LENGTH,
                address.getCoordinatesLatitude() + SIZE_LENGTH,
                address.getCoordinatesLatitude() - SIZE_LENGTH);

        if(routeList != null && !routeList.isEmpty()) {
            for(Route route: routeList){
                List<InventoryItem> inventoryItems = inventoryService.findAllInventoryItemsByAddressAndItemType(
                        route.getToAddress(), requestItem.getItemType());
                if(inventoryItems != null && !inventoryItems.isEmpty()){
                    InventoryItem inventoryItem = getClosestInventoryItem(inventoryItems, route.getFromAddress());
                    requestItem.setRequestStatus(RequestStatus.IN_PROCESS);
                    Volunteer giverRepresentative = inventoryService.getRepresentative(inventoryItem);
                    route.setInventoryItem(inventoryItem);
                    route.setRequestItem(requestItem);
                    route.setVolunteerGiver(giverRepresentative);
                    route.setVolunteerTaker(takerRepresentative);
                    route.setRouteStatus(RouteStatus.SET);
                    inventoryItem.setReadyToSend(false);
                    return;
                }
            }

        }
    }

    @Async
    public void setItemToRoute(Route route){
        List<InventoryItem> inventoryItems = inventoryService.findAllInventoryItemsByAddress(route.getFromAddress());
        List<RequestItem> requestItems = requestService.findAllRequestItemsByAddress(route.getToAddress());

        for(InventoryItem inventoryItem: inventoryItems){
            for (RequestItem requestItem: requestItems){
                if(doesInventoryItemFitsRequestItem(inventoryItem, requestItem)){
                    inventoryItem.setReadyToSend(false);
                    if(!requestItem.getRequestStatus().equals(RequestStatus.IN_PROCESS))
                        requestItem.setRequestStatus(RequestStatus.IN_PROCESS);
                    Volunteer takerRepresentative = requestService.getRepresentative(requestItem);
                    Volunteer giverRepresentative = inventoryService.getRepresentative(inventoryItem);
                    route.setInventoryItem(inventoryItem);
                    route.setRequestItem(requestItem);
                    route.setVolunteerGiver(giverRepresentative);
                    route.setVolunteerTaker(takerRepresentative);
                    route.setRouteStatus(RouteStatus.SET);
                    return;
                }
            }
        }
    }

    private boolean doesInventoryItemFitsRequestItem(InventoryItem inventoryItem, RequestItem requestItem){
        return inventoryItem.getItemType().equals(requestItem.getItemType());
    }

    private InventoryItem getClosestInventoryItem(List<InventoryItem> inventoryItems, Address from){
        return inventoryItems.stream()
                .min(Comparator.comparingDouble(x -> addressService.getAddress(x).compareTo(from)))
                .get();    }

    private RequestItem getClosestRequestItem(List<RequestItem> requestItems, Address to){
        return requestItems.stream()
                .min(Comparator.comparingDouble(x -> addressService.getAddress(x).compareTo(to)))
                .get();
    }
}
