package com.application.volunteers.inventory.service;

import com.application.volunteers.inventory.model.Inventory;
import com.application.volunteers.item.dto.RequestItemDto;
import com.application.volunteers.item.dto.ResponseItemDto;

import java.util.List;
import java.util.UUID;

public interface InventoryService {

    List<ResponseItemDto> findAllItems(Inventory inventory);

    List<ResponseItemDto> findAllItems(UUID volunteerId, UUID groupId);

    void saveItem(RequestItemDto requestItemDto, UUID volunteerId, UUID groupId);

    void updateItem(RequestItemDto requestItemDto, UUID volunteerId, UUID groupId, UUID itemId);

    void deleteItem(UUID volunteerId, UUID groupId, UUID itemId);

    void deleteInventoryById(UUID inventoryId);
}
