package com.application.content.groups.group.service;

import com.application.content.groups.group.dto.RequestGroupDto;
import com.application.content.groups.group.dto.response.ResponseGroupDto;
import com.application.content.groups.group.model.Group;
import com.application.content.groups.member.dto.RequestFindGroupsByAddressDto;

import java.util.List;
import java.util.UUID;

public interface GroupService {

    Group getGroup(UUID groupId);

    List<ResponseGroupDto> getGroups(UUID volunteerId);

    List<ResponseGroupDto> getGroupsByLocation(RequestFindGroupsByAddressDto requestFindGroupsByAddressDto);

    ResponseGroupDto getResponseGroupDto(UUID volunteerId, UUID groupId);

    void updateGroup(RequestGroupDto groupDto, UUID volunteerId, UUID groupId);

    void saveGroup(RequestGroupDto groupDto, UUID volunteerId);

    void deleteGroup(UUID volunteerId, UUID groupId);

    Group eitherIsAGroupAdmin(UUID volunteerId, UUID groupId) throws RuntimeException;

    Group eitherIsAGroupModerator(UUID volunteerId, UUID groupId) throws RuntimeException;
}
