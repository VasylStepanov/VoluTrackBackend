package com.application.content.groups.group.controller;

import com.application.content.address.dto.RequestAddressDto;
import com.application.content.address.service.AddressService;
import com.application.content.groups.group.dto.RequestGroupDto;
import com.application.content.groups.group.model.Group;
import com.application.content.groups.group.service.GroupService;
import com.application.content.items.inventory.service.InventoryService;
import com.application.content.volunteers.volunteer.service.VolunteerService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/group")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class GroupController {

    @Autowired
    GroupService groupService;

    @Autowired
    InventoryService inventoryService;

    @Autowired
    AddressService addressService;

    @Autowired
    VolunteerService volunteerService;

    @Operation(summary = "Get group by id", description = "Owner gets more data than other users")
    @GetMapping
    public ResponseEntity<?> getGroupById(@RequestParam UUID groupId, HttpServletRequest httpServletRequest){
        UUID volunteerId = volunteerService.getVolunteerId(httpServletRequest);
        return ResponseEntity.ok(groupService.getResponseGroupDto(volunteerId, groupId));
    }

    @Operation(summary = "Get group by volunteer id", description = "User get all his groups")
    @GetMapping("/getAllGroups")
    public ResponseEntity<?> getGroupByVolunteerId(HttpServletRequest httpServletRequest){
        UUID volunteerId = volunteerService.getVolunteerId(httpServletRequest);
        return ResponseEntity.ok(groupService.getGroups(volunteerId));
    }

    @Operation(summary = "Save group")
    @PostMapping("/save")
    public ResponseEntity<?> saveGroup(@RequestBody RequestGroupDto requestGroupDto, HttpServletRequest httpServletRequest){
        UUID volunteerId = volunteerService.getVolunteerId(httpServletRequest);
        groupService.saveGroup(requestGroupDto, volunteerId);
        return ResponseEntity.ok("Group is successfully added");
    }

    @Operation(summary = "Update group")
    @PostMapping("/update")
    public ResponseEntity<?> updateGroup(@RequestBody RequestGroupDto requestGroupDto, @RequestParam UUID groupId, HttpServletRequest httpServletRequest){
        UUID volunteerId = volunteerService.getVolunteerId(httpServletRequest);
        groupService.updateGroup(requestGroupDto, volunteerId, groupId);
        return ResponseEntity.ok("Group is successfully updated");
    }

    @Operation(summary = "Delete group")
    @PostMapping("/delete")
    public ResponseEntity<?> deleteGroup(@RequestParam UUID groupId, HttpServletRequest httpServletRequest){
        UUID volunteerId = volunteerService.getVolunteerId(httpServletRequest);
        groupService.deleteGroup(volunteerId, groupId);
        return ResponseEntity.ok("Group is successfully removed");
    }

    /*
    * Address endpoints
    * */

    @Operation(summary = "Update group's address", description = "If it's first request, than address is saved, next requests will update the address.")
    @PostMapping("/address/update")
    public ResponseEntity<?> updateAddress(@RequestBody RequestAddressDto requestAddressDto, @RequestParam UUID groupId, HttpServletRequest httpServletRequest){
        UUID volunteerId = volunteerService.getVolunteerId(httpServletRequest);
        Group group = groupService.eitherIsAGroupRepresent(volunteerId, groupId);
        addressService.updateAddress(group, requestAddressDto);
        return ResponseEntity.ok("Address is updated");
    }

}
