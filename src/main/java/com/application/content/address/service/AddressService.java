package com.application.content.address.service;

import com.application.content.address.dto.RequestAddressDto;
import com.application.content.address.model.IAddress;

import java.util.UUID;

public interface AddressService {

    void saveAddress(IAddress iAddress, RequestAddressDto requestAddressDto);

    void updateAddress(IAddress iAddress, RequestAddressDto requestAddressDto);

    void deleteAddress(UUID addressId);
}
