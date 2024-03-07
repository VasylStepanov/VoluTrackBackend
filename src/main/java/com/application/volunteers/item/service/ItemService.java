package com.application.volunteers.item.service;

import com.application.volunteers.item.dto.RequestItemDto;
import com.application.volunteers.item.dto.ResponseItemDto;
import com.application.volunteers.item.model.ItemMeasurement;
import com.application.volunteers.item.model.ItemType;

import java.util.List;
import java.util.UUID;

public interface ItemService {

    List<ResponseItemDto> findAllItems(UUID volunteerId);

    List<ResponseItemDto> findAllItemsByUserEmail(String email);

    ResponseItemDto getItemById(UUID itemId);

    List<ItemType> getAllItemTypes();

    List<ItemMeasurement> getAllItemMeasurements();

    void saveItem(UUID volunteerId, RequestItemDto requestItemDto);

    void updateItem(UUID volunteerId, UUID itemId, RequestItemDto requestItemDto);

    void deleteItemById(UUID volunteerId, UUID itemId);
}
