package com.application.content.groups.member.repository;

import com.application.content.groups.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface MemberRepository extends JpaRepository<Member, UUID> {

    Optional<List<Member>> findByVolunteerId(UUID volunteerId);

    Optional<Member> findByVolunteerIdAndGroupId(UUID volunteerId, UUID groupId);

}
