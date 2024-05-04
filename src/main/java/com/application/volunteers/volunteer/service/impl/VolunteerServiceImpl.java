package com.application.volunteers.volunteer.service.impl;

import com.application.security.service.JwtService;
import com.application.security.util.CookieUtil;
import com.application.user.model.User;
import com.application.volunteers.address.repository.AddressRepository;
import com.application.volunteers.inventory.model.Inventory;
import com.application.volunteers.inventory.repository.InventoryRepository;
import com.application.volunteers.request.model.Request;
import com.application.volunteers.request.repository.RequestRepository;
import com.application.volunteers.volunteer.dto.VolunteerProfileDto;
import com.application.volunteers.volunteer.dto.VolunteerPublicProfileDto;
import com.application.volunteers.volunteer.model.Volunteer;
import com.application.volunteers.volunteer.repository.VolunteerRepository;
import com.application.volunteers.volunteer.service.VolunteerService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AccessLevel;
import lombok.SneakyThrows;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Component
@FieldDefaults(level = AccessLevel.PROTECTED)
public class VolunteerServiceImpl implements VolunteerService {

    @Autowired
    JwtService jwtService;

    @Autowired
    CookieUtil cookieUtil;

    @Autowired
    VolunteerRepository volunteerRepository;

    @Autowired
    InventoryRepository inventoryRepository;

    @Autowired
    RequestRepository requestRepository;

    @Autowired
    AddressRepository addressRepository;

    @Override
    @SneakyThrows
    public Volunteer getVolunteer(UUID volunteerId) {
        return volunteerRepository
                .findById(volunteerId)
                .orElseThrow(() -> new RuntimeException("There is no volunteer"));
    }

    @Override
    @SneakyThrows
    public UUID getVolunteerId(HttpServletRequest httpServletRequest){
        String token = cookieUtil.getAccessTokenCookie(httpServletRequest.getCookies());
        return jwtService.extractVolunteerId(token);
    }

    @Override
    @SneakyThrows
    public VolunteerProfileDto getPrivateProfileData(UUID volunteerId) {
        Volunteer volunteer = volunteerRepository.findById(volunteerId).orElseThrow(() -> new RuntimeException("Volunteer not found"));
        return VolunteerProfileDto.setupVolunteerProfileDto(volunteer);
    }

    @Override
    @SneakyThrows
    public VolunteerPublicProfileDto getPublicProfileData(UUID volunteerId) {
        Volunteer volunteer = volunteerRepository.findById(volunteerId).orElseThrow(() -> new RuntimeException("Volunteer not found"));
        return VolunteerPublicProfileDto.setupVolunteerPublicProfileDto(volunteer);
    }

    @Override
    @Transactional
    public void saveVolunteerProfile(User user) {
        Inventory inventory = inventoryRepository.save(new Inventory());
        Request request = requestRepository.save(new Request());
        volunteerRepository.save(Volunteer.builder()
                .user(user)
                .inventory(inventory)
                .request(request)
                .build());
    }

    @Override
    @Transactional
    public void deleteById(UUID volunteerId) {
        Volunteer volunteer = getVolunteer(volunteerId);
        if(volunteer.getAddress() != null)
            addressRepository.deleteById(volunteer.getAddress().getId());
        if(volunteer.getInventory() != null)
            inventoryRepository.deleteById(volunteer.getInventory().getId());
        if(volunteer.getRequest() != null)
            requestRepository.deleteById(volunteer.getRequest().getId());
    }
}
