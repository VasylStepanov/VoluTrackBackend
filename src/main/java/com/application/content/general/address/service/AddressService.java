package com.application.content.general.address.service;

import com.application.content.general.address.dto.RequestAddressDto;
import com.application.content.general.address.model.Address;
import com.application.content.general.address.model.IAddress;
import com.application.content.items.inventory.model.InventoryItem;
import com.application.content.items.request.model.RequestItem;
import lombok.SneakyThrows;

import java.util.UUID;

public interface AddressService {

    Address getAddress(UUID volunteerId, UUID groupId);

    Address getAddress(InventoryItem inventoryItem);

    Address getAddress(RequestItem requestItem);


    Address saveAddress(RequestAddressDto requestAddressDto);

    void saveAddress(IAddress iAddress, RequestAddressDto requestAddressDto);

    void updateAddress(IAddress iAddress, RequestAddressDto requestAddressDto);

    void deleteAddress(UUID addressId);
}
