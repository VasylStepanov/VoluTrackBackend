package com.application.volunteers.address.service;

import com.application.volunteers.address.dto.RequestAddressDto;
import com.application.volunteers.address.model.IAddress;

import java.util.UUID;

public interface AddressService {

    void saveAddress(IAddress iAddress, RequestAddressDto requestAddressDto);

    void updateAddress(IAddress iAddress, RequestAddressDto requestAddressDto);

    void deleteAddress(UUID addressId);
}
