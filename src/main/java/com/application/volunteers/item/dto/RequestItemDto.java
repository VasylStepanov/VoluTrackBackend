package com.application.volunteers.item.dto;

import com.application.volunteers.item.model.ItemMeasurement;
import com.application.volunteers.item.model.ItemType;

public record RequestItemDto(String name, String description, Integer amount, String itemMeasurement, String itemType) {
}
