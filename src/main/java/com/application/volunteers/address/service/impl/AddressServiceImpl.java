package com.application.volunteers.address.service.impl;

import com.application.volunteers.address.dto.RequestAddressDto;
import com.application.volunteers.address.model.Address;
import com.application.volunteers.address.model.IAddress;
import com.application.volunteers.address.repository.AddressRepository;
import com.application.volunteers.address.service.AddressService;
import com.application.volunteers.group.service.GroupService;
import com.application.volunteers.volunteer.service.VolunteerService;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AddressServiceImpl implements AddressService {

    @Autowired
    AddressRepository addressRepository;

    @Autowired
    AddressValidation addressValidation;

    @Autowired
    VolunteerService volunteerService;

    @Autowired
    GroupService groupService;

    @Override
    public void saveAddress(IAddress iAddress, RequestAddressDto requestAddressDto) {
        Address address = addressRepository.save(Address.builder()
            .region(addressValidation.eitherRegionIsValidFull(requestAddressDto.region()))
            .settlement(addressValidation.eitherSettlementIsValidFull(requestAddressDto.settlement()))
            .location(addressValidation.eitherLocationIsValidFull(requestAddressDto.location()))
            .coordinatesLatitude(addressValidation.eitherCoordinatesLatitudeFull(requestAddressDto.coordinatesLatitude()))
            .coordinatesLongitude(addressValidation.eitherCoordinatesLongitude(requestAddressDto.coordinatesLongitude()))
            .build());
        iAddress.setAddress(address);
    }

    @Override
    public void updateAddress(IAddress iAddress, RequestAddressDto requestAddressDto) {
        Address address = iAddress.getAddress();
        if(address == null) {
            saveAddress(iAddress, requestAddressDto);
            return;
        }
        address.isUpdated();
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

    @Override
    public void deleteAddress(UUID addressId) {
        addressRepository.deleteById(addressId);
    }
}
