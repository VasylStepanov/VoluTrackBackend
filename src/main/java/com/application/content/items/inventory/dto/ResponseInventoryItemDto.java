package com.application.content.items.inventory.dto;

import com.application.content.items.inventory.model.InventoryItem;
import com.application.content.items.item.model.ItemType;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ResponseInventoryItemDto {

    boolean endProduct;

    boolean readyToSend;

    String name;

    String description;

    Double weight;

    Integer amount;

    ItemType itemType;

    public static ResponseInventoryItemDto toResponseInventoryItemDto(InventoryItem inventoryItem){
        return new ResponseInventoryItemDto(
                inventoryItem.isEndProduct(),
                inventoryItem.isReadyToSend(),
                inventoryItem.getName(),
                inventoryItem.getDescription(),
                inventoryItem.getWeight(),
                inventoryItem.getAmount(),
                inventoryItem.getItemType());
    }
}
