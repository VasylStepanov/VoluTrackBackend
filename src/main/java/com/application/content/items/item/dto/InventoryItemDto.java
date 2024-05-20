package com.application.content.items.item.dto;

import com.application.content.items.item.model.ItemType;

public record InventoryItemDto(String name, String description, Integer weight, Integer amount, ItemType itemType) {
}
