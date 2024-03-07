package com.application.volunteers.volunteer.service.impl;

import com.application.user.dto.UserDto;
import com.application.user.model.User;
import com.application.user.service.UserService;
import com.application.volunteers.volunteer.dto.VolunteerProfileDto;
import com.application.volunteers.volunteer.dto.VolunteerSetDto;
import com.application.volunteers.volunteer.model.Volunteer;
import com.application.volunteers.volunteer.repository.VolunteerRepository;
import com.application.volunteers.volunteer.service.VolunteerService;
import lombok.AccessLevel;
import lombok.SneakyThrows;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.UUID;

@Component
@FieldDefaults(level = AccessLevel.PROTECTED)
public class VolunteerServiceImpl implements VolunteerService {

    @Autowired
    VolunteerRepository volunteerRepository;

    @Autowired
    UserService userService;

    @Override
    public void saveVolunteerProfile(User user) {
        volunteerRepository.save(Volunteer.builder()
                .user(user)
                .build());
    }

    @Override
    @SneakyThrows
    public Volunteer getVolunteer(UUID volunteerId) {
        return volunteerRepository
                .findById(volunteerId)
                .orElseThrow(() -> new RuntimeException("There is no volunteer"));
    }

    @Override
    @SneakyThrows
    public VolunteerProfileDto getProfile(UUID userId) {
        User user = userService.findById(userId);
        return setupVolunteerProfileDto(user);
    }

    @Override
    @SneakyThrows
    public VolunteerProfileDto getProfileByEmail(String email) {
        User user = userService.findUserByEmail(email);
        return setupVolunteerProfileDto(user);
    }

    @Override
    @SneakyThrows
    public void updateProfile(VolunteerSetDto profile, UUID userId) {
        User user = userService.findById(userId);
        Volunteer volunteer = user.getVolunteer();
        LocalDateTime updatedAt = volunteer.getUpdatedAt();
        if(updatedAt != null && updatedAt.plusDays(1).isAfter(LocalDateTime.now()))
            throw new RuntimeException("You have already updated account data, update will be available in: " + updatedAt.plusDays(1));
        volunteer.setUpdatedAt(LocalDateTime.now());
        UserDto userDto = UserDto.builder()
                .id(userId)
                .firstName(profile.getFirstName())
                .lastName(profile.getLastName())
                .password(profile.getPassword())
                .build();
        userService.updateUser(user, userDto);
    }

    @Override
    public void deleteProfile(UUID userId) {
        userService.deleteById(userId);
    }

    private VolunteerProfileDto setupVolunteerProfileDto(User user){
        return new VolunteerProfileDto(
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getVolunteer().getHelpCounter(),
                user.getVolunteer().getCreatedAt()
        );
    }
}
