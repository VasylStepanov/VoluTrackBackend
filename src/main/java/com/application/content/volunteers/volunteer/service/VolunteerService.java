package com.application.content.volunteers.volunteer.service;

import com.application.content.volunteers.volunteer.dto.VolunteerProfileDto;
import com.application.content.volunteers.volunteer.dto.VolunteerPublicProfileDto;
import com.application.content.volunteers.volunteer.model.Volunteer;
import com.application.user.model.User;
import jakarta.servlet.http.HttpServletRequest;

import java.util.UUID;

public interface VolunteerService {

    Volunteer getVolunteer(UUID volunteerId);

    UUID getVolunteerId(HttpServletRequest httpServletRequest);

    VolunteerProfileDto getPrivateProfileData(UUID volunteerId);

    VolunteerPublicProfileDto getPublicProfileData(UUID volunteerId);

    void saveVolunteerProfile(User user);

    void deleteById(UUID volunteerId);
}
