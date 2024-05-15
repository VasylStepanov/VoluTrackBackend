package com.application.content.groups.member.dto;

import java.util.UUID;

public record RequestSetMemberRoleDto(String memberRole, UUID volunteerId, UUID groupId) {
}
