package com.application.volunteers.volunteer.service;

import com.application.user.model.User;
import com.application.volunteers.volunteer.dto.VolunteerProfileDto;
import com.application.volunteers.volunteer.dto.VolunteerPublicProfileDto;
import com.application.volunteers.volunteer.model.Volunteer;
import lombok.SneakyThrows;

import java.util.UUID;

public interface VolunteerService {

    Volunteer getVolunteer(UUID volunteerId);

    VolunteerProfileDto getPrivateProfileData(UUID volunteerId);

    VolunteerPublicProfileDto getPublicProfileData(UUID volunteerId);

    void saveVolunteerProfile(User user);

    void deleteById(UUID volunteerId);
}
