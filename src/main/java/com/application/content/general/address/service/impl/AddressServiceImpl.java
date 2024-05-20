package com.application.content.general.address.service.impl;

import com.application.content.general.address.model.Address;
import com.application.content.general.address.model.IAddress;
import com.application.content.general.address.dto.RequestAddressDto;
import com.application.content.general.address.repository.AddressRepository;
import com.application.content.general.address.service.AddressService;
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

    @Override
    public void saveAddress(IAddress iAddress, RequestAddressDto requestAddressDto) {
        Address address = addressRepository.save(Address.builder()
            .address(addressValidation.eitherAddressIsValidFull(requestAddressDto.address()))
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
        if(requestAddressDto.address() != null)
            address.setAddress(addressValidation.eitherAddressIsValid(requestAddressDto.address()));
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
