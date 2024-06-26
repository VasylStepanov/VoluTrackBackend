package com.application.content.groups.group.repository;

import com.application.content.groups.group.model.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface GroupRepository extends JpaRepository<Group, UUID> {

    List<Group> findAllByVolunteerId(UUID volunteerId);

    @Query("SELECT g FROM group g WHERE g.address.address = ?1")
    List<Group> findByAddress(String address);
}
