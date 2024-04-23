package com.application.volunteers.inventory.service;

import com.application.volunteers.inventory.model.Inventory;
import com.application.volunteers.item.dto.RequestItemDto;
import com.application.volunteers.item.dto.ResponseItemDto;
import com.application.volunteers.item.model.ItemMeasurement;
import com.application.volunteers.item.model.ItemType;
import lombok.SneakyThrows;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface InventoryService {

    List<ResponseItemDto> findAllItems(Inventory inventory);

    List<ResponseItemDto> findAllItemsByVolunteerId(UUID volunteerId);

    List<ResponseItemDto> findAllItemsByGroupId(UUID groupId);

    Set<ItemType> findAllItemTypes();

    Set<ItemMeasurement> findAllItemMeasurements();

    void saveItemByVolunteerId(RequestItemDto requestItemDto, UUID volunteerId);

    void saveItemByGroupId(RequestItemDto requestItemDto, UUID volunteerId, UUID groupId);

    void updateItemByVolunteerId(RequestItemDto requestItemDto, UUID volunteerId, UUID itemId);

    void updateItemByGroupId(RequestItemDto requestItemDto, UUID volunteerId, UUID groupId, UUID itemId);

    void deleteItemByVolunteerId(UUID volunteerId, UUID itemId);

    void deleteItemByGroupId(UUID volunteerId, UUID groupId, UUID itemId);

    void deleteInventoryById(UUID inventoryId);
}
