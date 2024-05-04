package com.application.content.groups.group.service;

import com.application.content.groups.group.dto.RequestGroupDto;
import com.application.content.groups.group.dto.response.ResponseGroupDto;
import com.application.content.groups.group.model.Group;

import java.util.List;
import java.util.UUID;

public interface GroupService {

    Group getGroup(UUID groupId);

    List<ResponseGroupDto> getGroups(UUID volunteerId);

    ResponseGroupDto getResponseGroupDto(UUID volunteerId, UUID groupId);

    void updateGroup(RequestGroupDto groupDto, UUID volunteerId, UUID groupId);

    void saveGroup(RequestGroupDto groupDto, UUID volunteerId);

    void deleteGroup(UUID volunteerId, UUID groupId);

    Group eitherIsAGroupRepresent(UUID volunteerId, UUID groupId) throws RuntimeException;
}
