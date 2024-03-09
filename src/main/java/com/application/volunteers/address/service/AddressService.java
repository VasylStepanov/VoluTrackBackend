package com.application.volunteers.address.service;

import com.application.volunteers.address.dto.RequestAddressDto;
import com.application.volunteers.address.dto.ResponseAddressDto;

import java.util.UUID;

public interface AddressService {


    ResponseAddressDto getAddressById(UUID addressId);

    ResponseAddressDto getAddressByEmail(String email);

    void saveAddress(UUID volunteerId, RequestAddressDto requestAddressDto);

    void updateAddress(UUID volunteerId, RequestAddressDto requestAddressDto);

}
