package com.application.content.items.inventory.service;

import com.application.content.general.address.model.Address;
import com.application.content.items.inventory.dto.InventoryItemDto;
import com.application.content.items.inventory.dto.ResponseInventoryItemDto;
import com.application.content.items.inventory.model.InventoryItem;
import com.application.content.items.item.model.ItemType;
import com.application.content.volunteers.volunteer.model.Volunteer;

import java.util.List;
import java.util.UUID;

public interface InventoryService {

    List<InventoryItem> findAllInventoryItemsByAddress(Address address);

    List<InventoryItem> findAllInventoryItemsByAddressAndItemType(Address address, ItemType itemType);

    Volunteer getRepresentative(InventoryItem inventoryItem);

    List<ResponseInventoryItemDto> findAllItems(UUID volunteerId, UUID groupId);

    void saveItem(InventoryItemDto requestItemDto, UUID volunteerId, UUID groupId);

    void updateItem(InventoryItemDto requestItemDto, UUID volunteerId, UUID groupId, UUID itemId);

    void updateReadyToSend(boolean readyToSend, UUID volunteerId, UUID groupId, UUID inventoryItemId);

    void deleteItem(UUID volunteerId, UUID groupId, UUID itemId);

    void deleteInventoryById(UUID inventoryId);
}
