package com.application.volunteers.group.dto.response;

import com.application.volunteers.address.dto.ResponsePrivateAddressDto;
import com.application.volunteers.group.model.Group;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import java.util.UUID;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ResponsePrivateGroupDto extends ResponseGroupDto {

    ResponsePrivateAddressDto responsePrivateAddressDto;

    public ResponsePrivateGroupDto(UUID id, String name, String description, int helpCounter, ResponsePrivateAddressDto responsePrivateAddressDto) {
        super(id, name, description, helpCounter);
        this.responsePrivateAddressDto = responsePrivateAddressDto;
    }

    public static ResponsePrivateGroupDto toResponseGroupDto(Group group) {
        return new ResponsePrivateGroupDto(
                group.getId(),
                group.getName(),
                group.getDescription(),
                group.getHelpCounter(),
                ResponsePrivateAddressDto.toResponseAddressDto(group.getAddress()));
    }
}
