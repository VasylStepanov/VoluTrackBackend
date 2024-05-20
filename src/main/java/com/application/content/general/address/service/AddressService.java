package com.application.content.general.address.service;

import com.application.content.general.address.dto.RequestAddressDto;
import com.application.content.general.address.model.IAddress;

import java.util.UUID;

public interface AddressService {

    void saveAddress(IAddress iAddress, RequestAddressDto requestAddressDto);

    void updateAddress(IAddress iAddress, RequestAddressDto requestAddressDto);

    void deleteAddress(UUID addressId);
}
