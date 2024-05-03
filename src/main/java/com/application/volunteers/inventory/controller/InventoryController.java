package com.application.volunteers.inventory.controller;

import com.application.volunteers.group.service.GroupService;
import com.application.volunteers.inventory.service.InventoryService;
import com.application.volunteers.item.dto.RequestItemDto;
import com.application.volunteers.volunteer.service.VolunteerService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("api/v1/inventory")
public class InventoryController {

    @Autowired
    VolunteerService volunteerService;

    @Autowired
    InventoryService inventoryService;

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
                                     @RequestBody RequestItemDto requestItemDto,
                                     @RequestParam(required = false) UUID groupId){
        UUID volunteerId = volunteerService.getVolunteerId(httpServletRequest);

        inventoryService.saveItem(requestItemDto, volunteerId, groupId);
        return ResponseEntity.ok("Item is saved");
    }

    @Operation(summary = "Update item",
            description = """ 
                    Update item by group id or if group id is null, then update by volunteer id.
                    Only owner of the item can update the item.""")
    @PutMapping("/updateItem")
    public ResponseEntity<?> updateItem(HttpServletRequest httpServletRequest,
                                        @RequestBody RequestItemDto requestUpdateItemDto,
                                        @RequestParam(required = false) UUID groupId,
                                        @RequestParam UUID itemId){
        UUID volunteerId = volunteerService.getVolunteerId(httpServletRequest);
        inventoryService.updateItem(requestUpdateItemDto, volunteerId, groupId, itemId);
        return ResponseEntity.ok("Item is updated");
    }

    @Operation(summary = "Delete item by item id",
            description = """
                    Delete item by group id or if group id is null, then delete by volunteer id.
                    Only owner of the item can remove the item.""")
    @DeleteMapping("/deleteItem")
    public ResponseEntity<?> deleteItem(HttpServletRequest httpServletRequest,
                                        @RequestParam(required = false) UUID groupId,
                                        @RequestParam UUID itemId){
        UUID volunteerId = volunteerService.getVolunteerId(httpServletRequest);
        inventoryService.deleteItem(volunteerId, groupId, itemId);
        return ResponseEntity.ok("Item is removed");
    }


}
