package com.application.content.groups.member.controller;

import com.application.content.general.address.model.Address;
import com.application.content.groups.group.service.GroupService;
import com.application.content.groups.member.dto.RequestFindGroupsByAddressDto;
import com.application.content.groups.member.dto.RequestSetMemberRoleDto;
import com.application.content.groups.member.service.MemberService;
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
@RequestMapping("/api/v1/member")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MemberController {

    @Autowired
    VolunteerService volunteerService;

    @Autowired
    GroupService groupService;

    @Autowired
    MemberService memberService;

    @Operation(summary = "Find nearest groups.",
            description = """
                    If user set address then return a list of closest groups by user address.
                    Otherwise return \"Address isn't set\"""")
    @GetMapping("/findGroupsByAddress")
    public ResponseEntity<?> findGroups(HttpServletRequest httpServletRequest) {
        Address address = volunteerService.getVolunteer(volunteerService.getVolunteerId(httpServletRequest)).getAddress();
        if(address != null)
            return ResponseEntity.ok(groupService.getGroupsByLocation(
               new RequestFindGroupsByAddressDto(address.getAddress())
            ));
        throw new RuntimeException("Address is empty");
    }
    
    @Operation(summary = "Find user joined groups.",
            description = """
                    Return all groups what user joined.""")
    @GetMapping("/findJoinedGroups")
    public ResponseEntity<?> findJoinedGroups(HttpServletRequest httpServletRequest) {
        UUID volunteerId = volunteerService.getVolunteerId(httpServletRequest);
        return ResponseEntity.ok(memberService.listOfResponseGroupDto(volunteerId));
    }

    @Operation(summary = "Join group.",
            description = "Volunteer may join any group.")
    @PostMapping("/join")
    public ResponseEntity<?> joinGroup(@RequestParam UUID groupId,
                                       HttpServletRequest httpServletRequest) {
        UUID volunteerId = volunteerService.getVolunteerId(httpServletRequest);
        memberService.joinGroup(groupId, volunteerId);
        return ResponseEntity.ok("Successfully joined to the group");
    }

    @Operation(summary = "Set volunteer as a moderator.",
            description = "Only admin can set volunteer as a moderator.")
    @PutMapping("/setModer")
    public ResponseEntity<?> setMemberRole(@RequestBody RequestSetMemberRoleDto requestSetMemberRoleDto,
                                          HttpServletRequest httpServletRequest) {
        UUID volunteerId = volunteerService.getVolunteerId(httpServletRequest);
        memberService.setMemberRole(requestSetMemberRoleDto, volunteerId);
        return ResponseEntity.ok("Moderator role was set successfully");
    }
}
