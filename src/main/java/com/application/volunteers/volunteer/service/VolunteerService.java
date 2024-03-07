package com.application.volunteers.volunteer.service;

import com.application.user.model.User;
import com.application.volunteers.volunteer.dto.VolunteerProfileDto;
import com.application.volunteers.volunteer.dto.VolunteerSetDto;
import com.application.volunteers.volunteer.model.Volunteer;

import java.util.UUID;

public interface VolunteerService {

    Volunteer getVolunteer(UUID userId);
    VolunteerProfileDto getProfile(UUID userId);

    VolunteerProfileDto getProfileByEmail(String email);

    void updateProfile(VolunteerSetDto profile, UUID userId);

    void saveVolunteerProfile(User user);

    void deleteProfile(UUID uuid);
}
