package com.application.volunteers.volunteer.repository;

import com.application.volunteers.volunteer.model.Volunteer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface VolunteerRepository extends JpaRepository<Volunteer, UUID> {
}
