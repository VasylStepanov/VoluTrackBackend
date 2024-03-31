package com.application.volunteers.item.service;

import com.application.volunteers.inventory.model.Inventory;
import com.application.volunteers.item.dto.RequestItemDto;
import com.application.volunteers.item.dto.ResponseItemDto;
import com.application.volunteers.item.model.ItemMeasurement;
import com.application.volunteers.item.model.ItemType;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface ItemService {

    List<ResponseItemDto> findAllItems(UUID inventoryId);

    void saveItem(RequestItemDto requestItemDto, Inventory inventory);

    void updateItem(RequestItemDto requestItemDto, UUID inventoryId, UUID itemId);

    void deleteItem(UUID inventoryId, UUID itemId);

}
