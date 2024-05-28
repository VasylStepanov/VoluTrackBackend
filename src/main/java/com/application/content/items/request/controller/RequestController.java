package com.application.content.items.request.controller;

import com.application.content.general.address.service.AddressService;
import com.application.content.general.route.service.RouteService;
import com.application.content.items.request.dto.RequestItemDto;
import com.application.content.items.request.model.RequestItem;
import com.application.content.items.request.service.RequestService;
import com.application.content.volunteers.volunteer.service.VolunteerService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("api/v1/request")
public class RequestController {

    @Autowired
    VolunteerService volunteerService;

    @Autowired
    RequestService requestService;

    @Autowired
    RouteService routeService;

    @Autowired
    AddressService addressService;

    @Operation(summary = "Get all request items",
            description = "Get request items by group id or if group id is null, then get by volunteer id.")
    @GetMapping("/getAllRequestItems")
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
    @PostMapping("/addRequestItem")
    @Transactional
    public ResponseEntity<?> addRequestItem(HttpServletRequest httpServletRequest,
                                     @RequestBody RequestItemDto requestItemDto,
                                     @RequestParam(required = false) UUID groupId){
        UUID volunteerId = volunteerService.getVolunteerId(httpServletRequest);
        if(addressService.getAddress(volunteerId, groupId) == null)
            throw new RuntimeException("Can't add requests, set address first.");
        RequestItem requestItem = requestService.saveRequestItem(requestItemDto, volunteerId, groupId);
        routeService.setItemToRouteByRequestItem(requestItem);
        return ResponseEntity.ok("Request item is saved");
    }

    @Operation(summary = "Update request item",
            description = """ 
                    Update request item by group id or if group id is null, then update by volunteer id.
                    Only owner of the item can update the item.""")
    @PutMapping("/updateRequestItem")
    public ResponseEntity<?> updateRequestItem(HttpServletRequest httpServletRequest,
                                        @RequestBody RequestItemDto requestUpdateItemDto,
                                        @RequestParam(required = false) UUID groupId,
                                        @RequestParam UUID requestItemId){
        UUID volunteerId = volunteerService.getVolunteerId(httpServletRequest);
        requestService.updateRequestItem(requestUpdateItemDto, volunteerId, groupId, requestItemId);
        return ResponseEntity.ok("Request item is updated");
    }

    @Operation(summary = "Delete request item by item id",
            description = """
                    Delete request item by group id or if group id is null, then delete by volunteer id.
                    Only owner of the item can remove the item.""")
    @DeleteMapping("/deleteRequestItem")
    public ResponseEntity<?> deleteRequestItem(HttpServletRequest httpServletRequest,
                                        @RequestParam(required = false) UUID groupId,
                                        @RequestParam UUID requestItemId){
        UUID volunteerId = volunteerService.getVolunteerId(httpServletRequest);
        requestService.deleteRequestItem(volunteerId, groupId, requestItemId);
        return ResponseEntity.ok("Request item is removed");
    }
}
