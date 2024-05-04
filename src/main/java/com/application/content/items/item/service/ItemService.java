package com.application.content.items.item.service;

import com.application.content.items.item.dto.RequestItemDto;
import com.application.content.items.item.dto.ResponseItemDto;
import com.application.content.items.item.model.ItemMeasurement;
import com.application.content.items.inventory.model.Inventory;
import com.application.content.items.item.model.Item;
import com.application.content.items.item.model.ItemType;
import com.application.content.items.request.model.Request;

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
