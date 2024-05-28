package com.application.content.items.request.dto;

import com.application.content.items.item.model.ItemType;

public record RequestItemDto(Boolean endProduct, Double weight, Integer amount, ItemType itemType) {
}
