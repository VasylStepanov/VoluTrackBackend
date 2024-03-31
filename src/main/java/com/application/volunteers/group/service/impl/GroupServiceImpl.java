package com.application.volunteers.group.service.impl;

import com.application.volunteers.address.repository.AddressRepository;
import com.application.volunteers.group.dto.RequestGroupDto;
import com.application.volunteers.group.dto.response.ResponseGroupDto;
import com.application.volunteers.group.dto.response.ResponsePrivateGroupDto;
import com.application.volunteers.group.dto.response.ResponsePublicGroupDto;
import com.application.volunteers.group.model.Group;
import com.application.volunteers.group.repository.GroupRepository;
import com.application.volunteers.group.service.GroupService;
import com.application.volunteers.inventory.model.Inventory;
import com.application.volunteers.inventory.repository.InventoryRepository;
import com.application.volunteers.inventory.service.InventoryService;
import com.application.volunteers.volunteer.model.Volunteer;
import com.application.volunteers.volunteer.service.VolunteerService;
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
        groupRepository.save(Group.builder()
            .name(groupValidation.eitherNameIsValidFull(groupDto.name()))
            .description(groupValidation.eitherDescriptionIsValidFull(groupDto.description()))
            .inventory(inventory)
            .volunteer(volunteer)
            .build());
    }

    @Override
    @SneakyThrows
    @Transactional
    public void updateGroup(RequestGroupDto groupDto, UUID volunteerId, UUID groupId) {
        Group group = eitherIsGroupOwner(volunteerId, groupId);
        if(groupDto.name() != null)
            group.setName(groupValidation.eitherNameIsValid(groupDto.name()));
        if(groupDto.description() != null)
            group.setDescription(groupValidation.eitherDescriptionIsValid(groupDto.description()));
    }

    @Override
    @Transactional
    public void deleteGroup(UUID volunteerId, UUID groupId) {
        Group group = eitherIsGroupOwner(volunteerId, groupId);
        if(group.getAddress() != null)
            addressRepository.deleteById(group.getAddress().getId());
        if(group.getInventory() != null)
            inventoryRepository.deleteById(group.getInventory().getId());
        groupRepository.delete(group);
    }

    @Override
    public Group eitherIsGroupOwner(UUID volunteerId, UUID groupId) throws RuntimeException {
        Group group = getGroup(groupId);
        if(group.getVolunteer().getId().equals(volunteerId))
            return group;
        throw new RuntimeException("User isn't an owner of the group");
    }
}
