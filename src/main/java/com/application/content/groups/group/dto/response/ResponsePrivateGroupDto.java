package com.application.content.groups.group.dto.response;

import com.application.content.general.address.dto.ResponsePrivateAddressDto;
import com.application.content.groups.group.model.Group;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import java.util.UUID;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ResponsePrivateGroupDto extends ResponseGroupDto {

    ResponsePrivateAddressDto responsePrivateAddressDto;

    public ResponsePrivateGroupDto(UUID id, String name, String description, ResponsePrivateAddressDto responsePrivateAddressDto) {
        super(id, name, description);
        this.responsePrivateAddressDto = responsePrivateAddressDto;
    }

    public static ResponsePrivateGroupDto toResponseGroupDto(Group group) {
        return new ResponsePrivateGroupDto(
                group.getId(),
                group.getName(),
                group.getDescription(),
                ResponsePrivateAddressDto.toResponseAddressDto(group.getAddress()));
    }
}
