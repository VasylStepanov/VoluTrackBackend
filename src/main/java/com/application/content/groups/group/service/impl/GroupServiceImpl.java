package com.application.content.groups.group.service.impl;

import com.application.content.address.repository.AddressRepository;
import com.application.content.groups.group.dto.response.ResponsePrivateGroupDto;
import com.application.content.groups.group.dto.response.ResponsePublicGroupDto;
import com.application.content.groups.group.model.Group;
import com.application.content.groups.group.repository.GroupRepository;
import com.application.content.groups.group.service.GroupService;
import com.application.content.groups.group.dto.RequestGroupDto;
import com.application.content.groups.group.dto.response.ResponseGroupDto;
import com.application.content.items.inventory.model.Inventory;
import com.application.content.items.inventory.repository.InventoryRepository;
import com.application.content.items.request.model.Request;
import com.application.content.items.request.repository.RequestRepository;
import com.application.content.volunteers.volunteer.model.Volunteer;
import com.application.content.volunteers.volunteer.service.VolunteerService;
import lombok.AccessLevel;
import lombok.SneakyThrows;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
public class GroupServiceImpl implements GroupService {

    @Autowired
    InventoryRepository inventoryRepository;

    @Autowired
    RequestRepository requestRepository;

    @Autowired
    AddressRepository addressRepository;

    @Autowired
    GroupRepository groupRepository;

    @Autowired
    GroupValidation groupValidation;

    @Autowired
    VolunteerService volunteerService;

    @Override
    public Group getGroup(UUID groupId) {
        return groupRepository.findById(groupId).orElseThrow(() -> new RuntimeException("Group not found"));
    }

    @Override
    public List<ResponseGroupDto> getGroups(UUID volunteerId) {
        return groupRepository.findAllByVolunteerId(volunteerId)
                .stream()
                .map(ResponsePrivateGroupDto::toResponseGroupDto)
                .collect(Collectors.toList());
    }

    @Override
    public ResponseGroupDto getResponseGroupDto(UUID volunteerId, UUID groupId) {
        Group group = getGroup(groupId);
        if(group.getVolunteer().getId().equals(volunteerId))
            return ResponsePrivateGroupDto.toResponseGroupDto(group);
        else
            return ResponsePublicGroupDto.toResponsePublicGroupDto(group);
    }

    @Override
    @Transactional
    public void saveGroup(RequestGroupDto groupDto, UUID volunteerId) {
        Volunteer volunteer = volunteerService.getVolunteer(volunteerId);
        Inventory inventory = inventoryRepository.save(new Inventory());
        Request request = requestRepository.save(new Request());
        groupRepository.save(Group.builder()
            .name(groupValidation.eitherNameIsValidFull(groupDto.name()))
            .description(groupValidation.eitherDescriptionIsValidFull(groupDto.description()))
            .inventory(inventory)
            .request(request)
            .volunteer(volunteer)
            .build());
    }

    @Override
    @SneakyThrows
    @Transactional
    public void updateGroup(RequestGroupDto groupDto, UUID volunteerId, UUID groupId) {
        Group group = eitherIsAGroupRepresent(volunteerId, groupId);
        if(groupDto.name() != null)
            group.setName(groupValidation.eitherNameIsValid(groupDto.name()));
        if(groupDto.description() != null)
            group.setDescription(groupValidation.eitherDescriptionIsValid(groupDto.description()));
    }

    @Override
    @Transactional
    public void deleteGroup(UUID volunteerId, UUID groupId) {
        Group group = eitherIsAGroupRepresent(volunteerId, groupId);
        if(group.getAddress() != null)
            addressRepository.deleteById(group.getAddress().getId());
        if(group.getInventory() != null)
            inventoryRepository.deleteById(group.getInventory().getId());
        if(group.getRequest() != null)
            requestRepository.deleteById(group.getRequest().getId());
        groupRepository.delete(group);
    }

    @Override
    public Group eitherIsAGroupRepresent(UUID volunteerId, UUID groupId) throws RuntimeException {
        Group group = getGroup(groupId);
        if(group.getVolunteer().getId().equals(volunteerId))
            return group;
        throw new RuntimeException("User isn't an owner of the group");
    }
}
