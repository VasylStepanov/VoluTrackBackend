package com.application.content.items.item.dto;

import com.application.content.items.item.model.ItemType;

public record RequestItemDto(Integer amount, Integer weight, ItemType itemType) {
}
