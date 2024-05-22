package com.application.content.general.address.service.impl;

import com.application.content.general.address.model.Address;
import com.application.content.general.address.model.IAddress;
import com.application.content.general.address.dto.RequestAddressDto;
import com.application.content.general.address.repository.AddressRepository;
import com.application.content.general.address.service.AddressService;
import com.application.content.groups.group.model.Group;
import com.application.content.groups.group.service.GroupService;
import com.application.content.items.inventory.model.InventoryItem;
import com.application.content.items.request.model.RequestItem;
import com.application.content.volunteers.volunteer.model.Volunteer;
import com.application.content.volunteers.volunteer.service.VolunteerService;
import lombok.AccessLevel;
import lombok.SneakyThrows;
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

    @SneakyThrows
    @Override
    public Address getAddress(UUID volunteerId, UUID groupId) {
        if(groupId == null)
            return getVolunteerAddress(volunteerId);
        return getGroupAddress(volunteerId, groupId);
    }

    @Override
    public Address getAddress(InventoryItem inventoryItem) {
        if(inventoryItem.getInventory().getGroup() == null)
            return inventoryItem.getInventory().getVolunteer().getAddress();
        return inventoryItem.getInventory().getGroup().getAddress();
    }

    @Override
    public Address getAddress(RequestItem requestItem) {
        if(requestItem.getRequest().getGroup() == null)
            return requestItem.getRequest().getVolunteer().getAddress();
        return requestItem.getRequest().getGroup().getAddress();
    }

    @Override
    public Address saveAddress(RequestAddressDto requestAddressDto) {
        return addressRepository.save(Address.builder()
                .address(addressValidation.eitherAddressIsValidFull(requestAddressDto.address()))
                .coordinatesLongitude(addressValidation.eitherCoordinatesLongitude(requestAddressDto.coordinatesLongitude()))
                .coordinatesLatitude(addressValidation.eitherCoordinatesLatitudeFull(requestAddressDto.coordinatesLatitude()))
                .build());
    }

    @Override
    public void saveAddress(IAddress iAddress, RequestAddressDto requestAddressDto) {
        iAddress.setAddress(saveAddress(requestAddressDto));
    }

    @Override
    public void updateAddress(IAddress iAddress, RequestAddressDto requestAddressDto) {
        Address address = iAddress.getAddress();
        if(address == null) {
            saveAddress(iAddress, requestAddressDto);
            return;
        }
        address.isUpdated();
        if(requestAddressDto.address() != null)
            address.setAddress(addressValidation.eitherAddressIsValid(requestAddressDto.address()));
        if(requestAddressDto.coordinatesLongitude() != null)
            address.setCoordinatesLongitude(addressValidation.eitherCoordinatesLongitude(requestAddressDto.coordinatesLongitude()));
        if(requestAddressDto.coordinatesLatitude() != null)
            address.setCoordinatesLatitude(addressValidation.eitherCoordinatesLatitude(requestAddressDto.coordinatesLatitude()));
    }

    @Override
    public void deleteAddress(UUID addressId) {
        addressRepository.deleteById(addressId);
    }

    @SneakyThrows
    private Address getVolunteerAddress(UUID volunteerId) {
        Volunteer volunteer = volunteerService.getVolunteer(volunteerId);
        return volunteer.getAddress();
    }

    @SneakyThrows
    private Address getGroupAddress(UUID volunteerId, UUID groupId) {
        Group group = groupService.eitherIsAGroupModerator(volunteerId, groupId);
        return group.getAddress();
    }
}
