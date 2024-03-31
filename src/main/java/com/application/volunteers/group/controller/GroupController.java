package com.application.volunteers.group.controller;

import com.application.security.service.JwtService;
import com.application.security.util.CookieUtil;
import com.application.volunteers.address.dto.RequestAddressDto;
import com.application.volunteers.address.model.Address;
import com.application.volunteers.address.service.AddressService;
import com.application.volunteers.group.dto.RequestGroupDto;
import com.application.volunteers.group.model.Group;
import com.application.volunteers.group.service.GroupService;
import com.application.volunteers.inventory.service.InventoryService;
import com.application.volunteers.item.dto.RequestItemDto;
import com.application.volunteers.volunteer.model.Volunteer;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AccessLevel;
import lombok.SneakyThrows;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/group")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class GroupController {

    @Autowired
    CookieUtil cookieUtil;

    @Autowired
    JwtService jwtService;

    @Autowired
    GroupService groupService;

    @Autowired
    InventoryService inventoryService;

    @Autowired
    AddressService addressService;

    @Operation(summary = "Get group by id", description = "Owner gets more data than other users")
    @GetMapping
    public ResponseEntity<?> getGroupById(@RequestParam UUID groupId, HttpServletRequest httpServletRequest){
        UUID volunteerId = getVolunteerId(httpServletRequest);
        return ResponseEntity.ok(groupService.getResponseGroupDto(volunteerId, groupId));
    }

    @Operation(summary = "Get group by volunteer id", description = "User get all his groups")
    @GetMapping("/getAllGroups")
    public ResponseEntity<?> getGroupByVolunteerId(HttpServletRequest httpServletRequest){
        UUID volunteerId = getVolunteerId(httpServletRequest);
        return ResponseEntity.ok(groupService.getGroups(volunteerId));
    }

    @Operation(summary = "Save group")
    @PostMapping("/save")
    public ResponseEntity<?> saveGroup(@RequestBody RequestGroupDto requestGroupDto, HttpServletRequest httpServletRequest){
        UUID volunteerId = getVolunteerId(httpServletRequest);
        groupService.saveGroup(requestGroupDto, volunteerId);
        return ResponseEntity.ok("Group is successfully added");
    }

    @Operation(summary = "Update group")
    @PostMapping("/update")
    public ResponseEntity<?> updateGroup(@RequestBody RequestGroupDto requestGroupDto, @RequestParam UUID groupId, HttpServletRequest httpServletRequest){
        UUID volunteerId = getVolunteerId(httpServletRequest);
        groupService.updateGroup(requestGroupDto, volunteerId, groupId);
        return ResponseEntity.ok("Group is successfully updated");
    }

    @Operation(summary = "Delete group")
    @PostMapping("/delete")
    public ResponseEntity<?> deleteGroup(@RequestParam UUID groupId, HttpServletRequest httpServletRequest){
        UUID volunteerId = getVolunteerId(httpServletRequest);
        groupService.deleteGroup(volunteerId, groupId);
        return ResponseEntity.ok("Group is successfully removed");
    }

    /*
    * Address endpoints
    * */

    @Operation(summary = "Update group's address", description = "If it's first request, than address is saved, next requests will update the address.")
    @PostMapping("/address/update")
    public ResponseEntity<?> updateAddress(@RequestBody RequestAddressDto requestAddressDto, @RequestParam UUID groupId, HttpServletRequest httpServletRequest){
        UUID volunteerId = getVolunteerId(httpServletRequest);
        Group group = groupService.eitherIsGroupOwner(volunteerId, groupId);
        addressService.updateAddress(group, requestAddressDto);
        return ResponseEntity.ok("Address is updated");
    }

    /*
    * Item endpoints
    * */

    @Operation(summary = "Get all items by group id.")
    @GetMapping("/inventory/getAllItems")
    public ResponseEntity<?> getAllItemsByGroupId(@RequestParam UUID id){
        return ResponseEntity.ok(inventoryService.findAllItemsByGroupId(id));
    }

    @Operation(summary = "Add item", description = "Only owner of the group or members who have an access can add items. Amount must be more than 0 but less than 65536. ItemType and ItemMeasurement are enum, there are two endpoints what responses their content.")
    @PostMapping("/inventory/addItem")
    public ResponseEntity<?> addItem(@RequestBody RequestItemDto requestItemDto, @RequestParam UUID groupId, HttpServletRequest httpServletRequest){
        UUID volunteerId = getVolunteerId(httpServletRequest);
        inventoryService.saveItemByGroupId(requestItemDto, volunteerId, groupId);
        return ResponseEntity.ok("Item is saved");
    }

    @Operation(summary = "Update item", description = "Only owner of the group or members who have an access can update the item.")
    @PostMapping("/inventory/updateItem")
    public ResponseEntity<?> updateItem(@RequestBody RequestItemDto requestItemDto,
                                        @RequestParam UUID groupId,
                                        @RequestParam UUID itemId,
                                        HttpServletRequest httpServletRequest){
        UUID volunteerId = getVolunteerId(httpServletRequest);
        inventoryService.updateItemByGroupId(requestItemDto, volunteerId, groupId, itemId);
        return ResponseEntity.ok("Item is updated");
    }

    @Operation(summary = "Delete item by item id", description = "Only owner of the group or members who have an access can delete the item.")
    @PostMapping("/inventory/deleteItem")
    public ResponseEntity<?> deleteItem(HttpServletRequest httpServletRequest,
                                        @RequestParam UUID groupId,
                                        @RequestParam UUID itemId){
        UUID volunteerId = getVolunteerId(httpServletRequest);
        inventoryService.deleteItemByGroupId(volunteerId, groupId, itemId);
        return ResponseEntity.ok("Item is removed");
    }

    @SneakyThrows
    private UUID getVolunteerId(HttpServletRequest httpServletRequest){
        String token = cookieUtil.getAccessTokenCookie(httpServletRequest.getCookies());
        return jwtService.extractVolunteerId(token);
    }
}
