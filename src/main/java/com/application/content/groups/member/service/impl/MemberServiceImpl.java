package com.application.content.groups.member.service.impl;

import com.application.content.groups.group.dto.response.ResponseGroupDto;
import com.application.content.groups.group.model.Group;
import com.application.content.groups.group.service.GroupService;
import com.application.content.groups.member.dto.RequestSetMemberRoleDto;
import com.application.content.groups.member.entity.Member;
import com.application.content.groups.member.entity.MemberRole;
import com.application.content.groups.member.repository.MemberRepository;
import com.application.content.groups.member.service.MemberService;
import com.application.content.volunteers.volunteer.model.Volunteer;
import com.application.content.volunteers.volunteer.service.VolunteerService;
import lombok.AccessLevel;
import lombok.SneakyThrows;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MemberServiceImpl implements MemberService {

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    GroupService groupService;

    @Autowired
    VolunteerService volunteerService;

    @Override
    public List<ResponseGroupDto> listOfResponseGroupDto(UUID volunteerId) {
        Volunteer volunteer = volunteerService.getVolunteer(volunteerId);
        return volunteer.getMemberInGroups().stream()
                .map(Member::getGroup)
                .map(ResponseGroupDto::toResponseGroupDto)
                .collect(Collectors.toList());
    }

    @Override
    @SneakyThrows
    public void joinGroup(UUID groupId, UUID volunteerId){
        Group group = groupService.getGroup(groupId);
        Volunteer volunteer = volunteerService.getVolunteer(volunteerId);
        Member member = Member.builder()
                .volunteer(volunteer)
                .group(group)
                .memberRole(MemberRole.MEMBER_ROLE)
                .build();
        memberRepository.save(member);
    }

    @Override
    @SneakyThrows
    public void setMemberRole(RequestSetMemberRoleDto requestSetMemberRoleDto, UUID volunteerId) {
        MemberRole memberRole = MemberRole.valueOfCheckedFull(requestSetMemberRoleDto.memberRole());
        if(memberRole.equals(MemberRole.ADMIN_ROLE))
            throw new RuntimeException("Can't set admin role");

        groupService.eitherIsAGroupAdmin(volunteerId, requestSetMemberRoleDto.groupId());
        Volunteer volunteer = volunteerService.getVolunteer(requestSetMemberRoleDto.volunteerId());
        Member member = volunteer.getMemberInGroups().stream()
                .filter(x -> x.getGroup().getId().equals(requestSetMemberRoleDto.groupId()))
                .findFirst().orElseThrow(() -> new RuntimeException("User is not a member of this group"));

        if(member.getMemberRole().equals(memberRole))
            throw new RuntimeException("User already has this role");
        member.setMemberRole(memberRole);
    }
}
