package com.application.volunteers.address.service.impl;

import com.application.user.model.User;
import com.application.user.service.UserService;
import com.application.volunteers.address.dto.RequestAddressDto;
import com.application.volunteers.address.dto.ResponseAddressDto;
import com.application.volunteers.address.model.Address;
import com.application.volunteers.address.repository.AddressRepository;
import com.application.volunteers.address.service.AddressService;
import com.application.volunteers.volunteer.model.Volunteer;
import com.application.volunteers.volunteer.service.VolunteerService;
import lombok.AccessLevel;
import lombok.SneakyThrows;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AddressServiceImpl implements AddressService {

    @Autowired
    AddressRepository addressRepository;

    @Autowired
    AddressValidation addressValidation;

    @Autowired
    UserService userService;

    @Autowired
    VolunteerService volunteerService;

    @Override
    @SneakyThrows
    public ResponseAddressDto getAddressById(UUID volunteerId) {
        return ResponseAddressDto.toResponseAddressDto(getAddress(volunteerId));
    }

    @Override
    public ResponseAddressDto getAddressByEmail(String email) {
        User user = userService.findUserByEmail(email);
        return ResponseAddressDto.toResponseAddressDto(getAddress(user.getVolunteer().getId()));
    }

    @Override
    public void saveAddress(UUID volunteerId, RequestAddressDto requestAddressDto) {
        Volunteer volunteer = volunteerService.getVolunteer(volunteerId);
        addressRepository.save(Address.builder()
            .region(addressValidation.eitherRegionIsValidFull(requestAddressDto.region()))
            .settlement(addressValidation.eitherSettlementIsValidFull(requestAddressDto.settlement()))
            .location(addressValidation.eitherLocationIsValidFull(requestAddressDto.location()))
            .coordinatesLatitude(addressValidation.eitherCoordinatesLatitudeFull(requestAddressDto.coordinatesLatitude()))
            .coordinatesLongitude(addressValidation.eitherCoordinatesLongitude(requestAddressDto.coordinatesLongitude()))
            .volunteer(volunteer)
            .build());
    }

    @Override
    public void updateAddress(UUID volunteerId, RequestAddressDto requestAddressDto) {
        Address address = getAddress(volunteerId);
        if(requestAddressDto.region() != null)
            address.setRegion(addressValidation.eitherRegionIsValid(requestAddressDto.region()));
        if(requestAddressDto.settlement() != null)
            address.setSettlement(addressValidation.eitherSettlementIsValid(requestAddressDto.settlement()));
        if(requestAddressDto.location() != null)
            address.setLocation(addressValidation.eitherLocationIsValid(requestAddressDto.location()));
        if(requestAddressDto.coordinatesLatitude() != null)
            address.setCoordinatesLatitude(addressValidation.eitherCoordinatesLatitude(requestAddressDto.coordinatesLatitude()));
        if(requestAddressDto.coordinatesLongitude() != null)
            address.setCoordinatesLongitude(addressValidation.eitherCoordinatesLongitude(requestAddressDto.coordinatesLongitude()));
    }

    private Address getAddress(UUID volunteerId) {
        return addressRepository.findByVolunteerId(volunteerId).orElseThrow(() -> new RuntimeException("Address not found by this id"));
    }
}
