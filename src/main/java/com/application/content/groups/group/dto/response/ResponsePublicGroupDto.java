package com.application.content.groups.group.dto.response;

import com.application.content.general.address.dto.ResponsePublicAddressDto;
import com.application.content.groups.group.model.Group;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import java.util.UUID;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ResponsePublicGroupDto extends ResponseGroupDto{

    ResponsePublicAddressDto responsePublicAddressDto;

    public ResponsePublicGroupDto(UUID id, String name, String description, ResponsePublicAddressDto responsePublicAddressDto) {
        super(id, name, description);
        this.responsePublicAddressDto = responsePublicAddressDto;
    }

    public static ResponsePublicGroupDto toResponsePublicGroupDto(Group group) {
        return new ResponsePublicGroupDto(
                group.getId(),
                group.getName(),
                group.getDescription(),
                ResponsePublicAddressDto.toResponsePublicAddressDto(group.getAddress()));
    }
}
