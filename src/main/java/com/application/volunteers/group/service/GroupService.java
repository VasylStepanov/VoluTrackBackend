package com.application.volunteers.group.service;

import com.application.volunteers.group.dto.RequestGroupDto;
import com.application.volunteers.group.dto.response.ResponseGroupDto;
import com.application.volunteers.group.model.Group;

import java.util.List;
import java.util.UUID;

public interface GroupService {

    Group getGroup(UUID groupId);

    List<ResponseGroupDto> getGroups(UUID volunteerId);

    ResponseGroupDto getResponseGroupDto(UUID volunteerId, UUID groupId);

    void updateGroup(RequestGroupDto groupDto, UUID volunteerId, UUID groupId);

    void saveGroup(RequestGroupDto groupDto, UUID volunteerId);

    void deleteGroup(UUID volunteerId, UUID groupId);

    Group eitherIsGroupOwner(UUID volunteerId, UUID groupId) throws RuntimeException;
}
