package com.application.content.items.request.dto;

import com.application.content.items.item.model.ItemType;

public record RequestItemDto(Boolean endProduct, Integer amount, Double weight, ItemType itemType) {
}
