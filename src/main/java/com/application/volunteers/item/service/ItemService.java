package com.application.volunteers.item.service;

import com.application.volunteers.inventory.model.Inventory;
import com.application.volunteers.item.dto.RequestItemDto;
import com.application.volunteers.item.dto.ResponseItemDto;
import com.application.volunteers.item.model.Item;
import com.application.volunteers.item.model.ItemMeasurement;
import com.application.volunteers.item.model.ItemType;
import com.application.volunteers.request.model.Request;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface ItemService {

    List<ResponseItemDto> getItems(Inventory inventory);

    List<ResponseItemDto> getItems(Request request);

    Set<ItemType> findAllItemTypes();

    Set<ItemMeasurement> findAllItemMeasurements();

    Item saveItem(RequestItemDto requestItemDto);

    void updateItem(RequestItemDto requestItemDto, Inventory inventory, UUID itemId);

    void updateItem(RequestItemDto requestItemDto, Request request, UUID itemId);

    void deleteItem(Inventory inventory, UUID itemId);

    void deleteItem(Request request, UUID itemId);

}
