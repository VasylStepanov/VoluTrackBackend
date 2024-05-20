package com.application.content.groups.group.service.impl;

import com.application.content.general.address.repository.AddressRepository;
import com.application.content.groups.group.dto.response.ResponsePrivateGroupDto;
import com.application.content.groups.group.dto.response.ResponsePublicGroupDto;
import com.application.content.groups.group.model.Group;
import com.application.content.groups.group.repository.GroupRepository;
import com.application.content.groups.group.service.GroupService;
import com.application.content.groups.group.dto.RequestGroupDto;
import com.application.content.groups.group.dto.response.ResponseGroupDto;
import com.application.content.groups.member.dto.RequestFindGroupsByAddressDto;
import com.application.content.groups.member.entity.Member;
import com.application.content.groups.member.entity.MemberRole;
import com.application.content.groups.member.repository.MemberRepository;
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
import java.util.Optional;
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
    MemberRepository memberRepository;

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
    public List<ResponseGroupDto> getGroupsByLocation(RequestFindGroupsByAddressDto requestFindGroupsByAddressDto) {
        return groupRepository.findByAddress(requestFindGroupsByAddressDto.address())
                .stream()
                .map(ResponseGroupDto::toResponseGroupDto)
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

        Group group = groupRepository.save(Group.builder()
            .name(groupValidation.eitherNameIsValidFull(groupDto.name()))
            .description(groupValidation.eitherDescriptionIsValidFull(groupDto.description()))
            .inventory(inventory)
            .request(request)
            .volunteer(volunteer)
            .build());

        Member member = Member.builder()
                .volunteer(volunteer)
                .group(group)
                .memberRole(MemberRole.ADMIN_ROLE)
                .build();

        memberRepository.save(member);
    }

    @Override
    @SneakyThrows
    @Transactional
    public void updateGroup(RequestGroupDto groupDto, UUID volunteerId, UUID groupId) {
        Group group = eitherIsAGroupAdmin(volunteerId, groupId);

        if(groupDto.name() != null)
            group.setName(groupValidation.eitherNameIsValid(groupDto.name()));

        if(groupDto.description() != null)
            group.setDescription(groupValidation.eitherDescriptionIsValid(groupDto.description()));
    }

    @Override
    @SneakyThrows
    @Transactional
    public void deleteGroup(UUID volunteerId, UUID groupId) {
        Group group = eitherIsAGroupAdmin(volunteerId, groupId);

        if(group.getAddress() != null)
            addressRepository.deleteById(group.getAddress().getId());

        if(group.getInventory() != null)
            inventoryRepository.deleteById(group.getInventory().getId());

        if(group.getRequest() != null)
            requestRepository.deleteById(group.getRequest().getId());

        groupRepository.delete(group);
    }

    @Override
    @SneakyThrows
    public Group eitherIsAGroupAdmin(UUID volunteerId, UUID groupId) throws RuntimeException {
        Member member = getMembership(volunteerId, groupId);

        if(member.getMemberRole().equals(MemberRole.ADMIN_ROLE))
            return member.getGroup();

        throw new RuntimeException("User is not an admin of this group");
    }

    @Override
    @SneakyThrows
    public Group eitherIsAGroupModerator(UUID volunteerId, UUID groupId) throws RuntimeException {
        Member member = getMembership(volunteerId, groupId);

        if(member.getMemberRole().equals(MemberRole.ADMIN_ROLE) ||
                member.getMemberRole().equals(MemberRole.MODER_ROLE))
            return member.getGroup();

        throw new RuntimeException("User isn't a moderator of this group");
    }

    @SneakyThrows
    private Member getMembership(UUID volunteerId, UUID groupId) throws RuntimeException {
        Optional<Member> member = memberRepository.findByVolunteerIdAndGroupId(volunteerId, groupId);
        return member.orElseThrow(() -> new RuntimeException("User is not related to this group"));
    }
}
