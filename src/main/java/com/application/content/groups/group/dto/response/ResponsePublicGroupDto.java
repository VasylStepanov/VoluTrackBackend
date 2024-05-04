package com.application.content.groups.group.dto.response;

import com.application.content.address.dto.ResponsePublicAddressDto;
import com.application.content.groups.group.model.Group;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import java.util.UUID;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ResponsePublicGroupDto extends ResponseGroupDto{

    ResponsePublicAddressDto responsePublicAddressDto;

    public ResponsePublicGroupDto(UUID id, String name, String description, int helpCounter, ResponsePublicAddressDto responsePublicAddressDto) {
        super(id, name, description, helpCounter);
        this.responsePublicAddressDto = responsePublicAddressDto;
    }

    public static ResponsePublicGroupDto toResponsePublicGroupDto(Group group) {
        return new ResponsePublicGroupDto(
                group.getId(),
                group.getName(),
                group.getDescription(),
                group.getHelpCounter(),
                ResponsePublicAddressDto.toResponsePublicAddressDto(group.getAddress()));
    }
}
