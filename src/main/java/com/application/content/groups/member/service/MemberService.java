package com.application.content.groups.member.service;

import com.application.content.groups.group.dto.response.ResponseGroupDto;
import com.application.content.groups.member.dto.RequestSetMemberRoleDto;

import java.util.List;
import java.util.UUID;

public interface MemberService {

    List<ResponseGroupDto> listOfResponseGroupDto(UUID volunteerId);

    void joinGroup(UUID groupId, UUID volunteerId);

    void setMemberRole(RequestSetMemberRoleDto requestSetMemberRoleDto, UUID volunteerId);
}
