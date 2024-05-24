package com.application.content.volunteers.volunteer.service;

import com.application.authentication.dto.RequestUpdateUserDataDto;
import com.application.content.volunteers.volunteer.dto.RequestVolunteerUpdateDto;
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

    void updateVolunteerProfile(RequestVolunteerUpdateDto requestVolunteerUpdateDto, UUID volunteerId);

    void deleteById(UUID volunteerId);
}
