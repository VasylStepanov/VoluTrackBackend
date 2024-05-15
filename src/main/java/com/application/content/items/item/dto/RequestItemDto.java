package com.application.content.items.item.dto;

public record RequestItemDto(String name,
                             String description,
                             Integer amount,
                             String itemMeasurement,
                             String itemType) {
}
