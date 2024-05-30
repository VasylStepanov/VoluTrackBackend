package com.application.content.items.inventory.controller;

import com.application.content.general.address.service.AddressService;
import com.application.content.general.route.service.RouteService;
import com.application.content.items.inventory.model.InventoryItem;
import com.application.content.items.inventory.service.InventoryService;
import com.application.content.items.inventory.dto.InventoryItemDto;
import com.application.content.volunteers.volunteer.service.VolunteerService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("api/v1/inventory")
public class InventoryController {

    @Autowired
    VolunteerService volunteerService;

    @Autowired
    InventoryService inventoryService;

    @Autowired
    RouteService routeService;

    @Autowired
    AddressService addressService;

    @Operation(summary = "Get all items",
            description = "Get items by group id or if group id is null, then get by volunteer id.")
    @GetMapping("/getAllItems")
    public ResponseEntity<?> getAllItemsByInventoryId(HttpServletRequest httpServletRequest,
                                                      @RequestParam(required = false) UUID groupId){
        UUID volunteerId = volunteerService.getVolunteerId(httpServletRequest);
        return ResponseEntity.ok(inventoryService.findAllItems(volunteerId, groupId));
    }

    @Operation(summary = "Add item",
            description = """
                    Add item by group id or if group id is null, then add by volunteer id.
                    Amount must be more than 0 but less than 65536.
                    ItemType and ItemMeasurement are enum, there are two endpoints what responses their content.""")
    @PostMapping("/addItem")
    public ResponseEntity<?> addItem(HttpServletRequest httpServletRequest,
                                     @RequestBody InventoryItemDto inventoryItemDto,
                                     @RequestParam(required = false) UUID groupId){
        UUID volunteerId = volunteerService.getVolunteerId(httpServletRequest);
        inventoryService.saveItem(inventoryItemDto, volunteerId, groupId);
        return ResponseEntity.ok("Item is saved");
    }

    @Operation(summary = "Update item, set ready to send")
    @PutMapping("/setReadyToSend")
    @Transactional
    public ResponseEntity<?> updateItemSetReadyToSend(HttpServletRequest httpServletRequest,
                                        @RequestParam boolean readyToSend,
                                        @RequestParam(required = false) UUID groupId,
                                        @RequestParam UUID inventoryItemId){
        UUID volunteerId = volunteerService.getVolunteerId(httpServletRequest);
        if(routeService.isInventoryItemInRoute(inventoryItemId))
            throw new RuntimeException("Can't update item, it's in road");
        if(addressService.getAddress(volunteerId, groupId) == null)
            throw new RuntimeException("Can't set item as ready to send, set address first.");
        InventoryItem inventoryItem = inventoryService
                .updateReadyToSend(readyToSend, volunteerId, groupId, inventoryItemId);
        if(readyToSend)
            routeService.setItemToRouteByInventoryItem(inventoryItem);
        return ResponseEntity.ok("Item ready_to_send is updated");
    }

    @Operation(summary = "Delete item by item id",
            description = """
                    Delete item by group id or if group id is null, then delete by volunteer id.
                    Only owner of the item can remove the item.""")
    @DeleteMapping("/deleteItem")
    public ResponseEntity<?> deleteItem(HttpServletRequest httpServletRequest,
                                        @RequestParam(required = false) UUID groupId,
                                        @RequestParam UUID inventoryItemId){
        UUID volunteerId = volunteerService.getVolunteerId(httpServletRequest);
        if(routeService.isInventoryItemInRoute(inventoryItemId))
            throw new RuntimeException("Inventory item can't be deleted, because item is in route");
        inventoryService.deleteItem(volunteerId, groupId, inventoryItemId);
        return ResponseEntity.ok("Item is removed");
    }
}
