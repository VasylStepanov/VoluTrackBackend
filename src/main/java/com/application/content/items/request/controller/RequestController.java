package com.application.content.items.request.controller;

import com.application.content.items.item.dto.RequestItemDto;
import com.application.content.items.request.service.RequestService;
import com.application.content.volunteers.volunteer.service.VolunteerService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("api/v1/request")
public class RequestController {

    @Autowired
    VolunteerService volunteerService;

    @Autowired
    RequestService requestService;

    @Operation(summary = "Get all request items",
            description = "Get request items by group id or if group id is null, then get by volunteer id.")
    @GetMapping("/getAllItems")
    public ResponseEntity<?> getAllItemsByInventoryId(HttpServletRequest httpServletRequest,
                                                      @RequestParam(required = false) UUID groupId){
        UUID volunteerId = volunteerService.getVolunteerId(httpServletRequest);
        return ResponseEntity.ok(requestService.findAllRequestItems(volunteerId, groupId));
    }

    @Operation(summary = "Add request item",
            description = """
                    Add request item by group id or if group id is null, then add by volunteer id.
                    Amount must be more than 0 but less than 65536.
                    ItemType and ItemMeasurement are enum, there are two endpoints what responses their content.""")
    @PostMapping("/addItem")
    public ResponseEntity<?> addItem(HttpServletRequest httpServletRequest,
                                     @RequestBody RequestItemDto requestItemDto,
                                     @RequestParam(required = false) UUID groupId){
        UUID volunteerId = volunteerService.getVolunteerId(httpServletRequest);

        requestService.saveRequestItem(requestItemDto, volunteerId, groupId);
        return ResponseEntity.ok("Item is saved");
    }

    @Operation(summary = "Update request item",
            description = """ 
                    Update request item by group id or if group id is null, then update by volunteer id.
                    Only owner of the item can update the item.""")
    @PutMapping("/updateItem")
    public ResponseEntity<?> updateItem(HttpServletRequest httpServletRequest,
                                        @RequestBody RequestItemDto requestUpdateItemDto,
                                        @RequestParam(required = false) UUID groupId,
                                        @RequestParam UUID itemId){
        UUID volunteerId = volunteerService.getVolunteerId(httpServletRequest);
        requestService.updateRequestItem(requestUpdateItemDto, volunteerId, groupId, itemId);
        return ResponseEntity.ok("Item is updated");
    }

    @Operation(summary = "Delete request item by item id",
            description = """
                    Delete request item by group id or if group id is null, then delete by volunteer id.
                    Only owner of the item can remove the item.""")
    @DeleteMapping("/deleteItem")
    public ResponseEntity<?> deleteItem(HttpServletRequest httpServletRequest,
                                        @RequestParam(required = false) UUID groupId,
                                        @RequestParam UUID itemId){
        UUID volunteerId = volunteerService.getVolunteerId(httpServletRequest);
        requestService.deleteRequestItem(volunteerId, groupId, itemId);
        return ResponseEntity.ok("Item is removed");
    }
}
