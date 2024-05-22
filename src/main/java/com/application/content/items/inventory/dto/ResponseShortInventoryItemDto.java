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
public class ResponseShortInventoryItemDto {

    boolean endProduct;

    String name;

    Double weight;

    Integer amount;

    ItemType itemType;

    public static ResponseShortInventoryItemDto toResponseShortInventoryItemDto(InventoryItem inventoryItem){
        if(inventoryItem == null)
            return null;
        return new ResponseShortInventoryItemDto(
                inventoryItem.isEndProduct(),
                inventoryItem.getName(),
                inventoryItem.getWeight(),
                inventoryItem.getAmount(),
                inventoryItem.getItemType());
    }
}
