package com.application.volunteers.item.dto;

import com.application.volunteers.item.model.Item;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class ResponseItemDto {

    UUID id;

    String name;

    String description;

    Integer amount;

    String itemMeasurement;

    String itemType;

    public static ResponseItemDto toResponseItemDto(Item item){
        return new ResponseItemDto(item.getId(),
                item.getName(),
                item.getDescription(),
                item.getAmount(),
                item.getItemMeasurement().name(),
                item.getItemType().name());
    }
}
