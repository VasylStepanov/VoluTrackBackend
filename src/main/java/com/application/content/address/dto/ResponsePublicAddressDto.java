package com.application.content.address.dto;

import com.application.content.address.model.Address;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;

@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ResponsePublicAddressDto {
    String region;

    String settlement;

    public static ResponsePublicAddressDto toResponsePublicAddressDto(Address address) {
        if(address == null)
            return null;
        return new ResponsePublicAddressDto(address.getRegion(),
                address.getSettlement());
    }
}
