package com.application.content.items.inventory.service;

import com.application.content.items.item.dto.InventoryItemDto;
import com.application.content.items.item.dto.ResponseInventoryItemDto;
import lombok.SneakyThrows;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

public interface InventoryService {

    List<ResponseInventoryItemDto> findAllItems(UUID volunteerId, UUID groupId);

    void saveItem(InventoryItemDto requestItemDto, UUID volunteerId, UUID groupId);

    void updateItem(InventoryItemDto requestItemDto, UUID volunteerId, UUID groupId, UUID itemId);

    void updateReadyToSend(UUID volunteerId, UUID groupId, UUID inventoryItemId);

    void deleteItem(UUID volunteerId, UUID groupId, UUID itemId);

    void deleteInventoryById(UUID inventoryId);
}
