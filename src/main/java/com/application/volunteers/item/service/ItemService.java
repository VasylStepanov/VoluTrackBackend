package com.application.volunteers.item.service;

import com.application.volunteers.inventory.model.Inventory;
import com.application.volunteers.item.dto.RequestItemDto;
import com.application.volunteers.item.dto.ResponseItemDto;
import com.application.volunteers.item.model.Item;

import java.util.List;
import java.util.UUID;

public interface ItemService {

    List<ResponseItemDto> getItemsFromInventory(Inventory inventory);

    Item saveItem(RequestItemDto requestItemDto);

    void updateItem(RequestItemDto requestItemDto, Inventory inventory, UUID itemId);

    void deleteItem(Inventory inventory, UUID itemId);

}
