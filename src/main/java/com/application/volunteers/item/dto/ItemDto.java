package com.application.volunteers.item.dto;

import com.application.volunteers.item.model.Item;
import com.application.volunteers.item.model.ItemMeasurement;
import com.application.volunteers.item.model.ItemType;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ItemDto {

    UUID id;

    String name;

    String description;

    Integer amount;

    ItemMeasurement itemMeasurement;

    ItemType itemType;

    LocalDateTime createdAt;

    LocalDateTime updatedAt;

    UUID volunteerId;

    public static ItemDto toItemDto(Item item){
        return new ItemDto(item.getId(),
                item.getName(),
                item.getDescription(),
                item.getAmount(),
                item.getItemMeasurement(),
                item.getItemType(),
                item.getCreatedAt(),
                item.getUpdatedAt(),
                item.getVolunteer().getId());
    }
}
