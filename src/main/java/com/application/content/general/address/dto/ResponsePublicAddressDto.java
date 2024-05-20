package com.application.content.general.address.dto;

import com.application.content.general.address.model.Address;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;

@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ResponsePublicAddressDto {
    String address;

    public static ResponsePublicAddressDto toResponsePublicAddressDto(Address address) {
        if(address == null)
            return null;
        return new ResponsePublicAddressDto(address.getAddress());
    }
}
