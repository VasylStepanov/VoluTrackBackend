package com.application.content.items.inventory.dto;

import com.application.content.items.item.model.ItemType;

public record InventoryItemDto(Boolean endProduct, String name, String description, Double weight, Integer amount, ItemType itemType) {
}
