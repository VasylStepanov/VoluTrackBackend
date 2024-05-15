package com.application.content.items.inventory.service.impl;

import com.application.content.items.inventory.model.Inventory;
import com.application.content.items.inventory.model.InventoryItem;
import com.application.content.items.inventory.repository.InventoryItemRepository;
import com.application.content.items.inventory.repository.InventoryRepository;
import com.application.content.items.inventory.service.InventoryService;
import com.application.content.groups.group.model.Group;
import com.application.content.groups.group.service.GroupService;
import com.application.content.items.item.dto.RequestItemDto;
import com.application.content.items.item.dto.ResponseItemDto;
import com.application.content.items.item.model.Item;
import com.application.content.items.item.service.ItemService;
import com.application.content.volunteers.volunteer.model.Volunteer;
import com.application.content.volunteers.volunteer.service.VolunteerService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class InventoryServiceImpl implements InventoryService {

    @Autowired
    VolunteerService volunteerService;

    @Autowired
    GroupService groupService;

    @Autowired
    ItemService itemService;

    @Autowired
    InventoryRepository inventoryRepository;

    @Autowired
    InventoryItemRepository inventoryItemRepository;

    @Override
    public List<ResponseItemDto> findAllItems(Inventory inventory) {
        return itemService.getItems(inventory);
    }

    @Override
    public List<ResponseItemDto> findAllItems(UUID volunteerId, UUID groupId) {
        Inventory inventory = getInventory(volunteerId, groupId);
        return findAllItems(inventory);
    }

    @Override
    @SneakyThrows
    public void saveItem(RequestItemDto requestItemDto, UUID volunteerId, UUID groupId) {
        Inventory inventory = getInventory(volunteerId, groupId);
        Item item = itemService.saveItem(requestItemDto);
        InventoryItem inventoryItem = InventoryItem.builder()
                .inventory(inventory)
                .item(item)
                .build();
        inventoryItemRepository.save(inventoryItem);
    }

    @Override
    @SneakyThrows
    public void updateItem(RequestItemDto requestItemDto, UUID volunteerId, UUID groupId, UUID itemId) {
        Inventory inventory = getInventory(volunteerId, groupId);
        itemService.updateItem(requestItemDto, inventory, itemId);
    }

    @Override
    public void deleteItem(UUID volunteerId, UUID groupId, UUID itemId) {
        Inventory inventory = getInventory(volunteerId, groupId);
        itemService.deleteItem(inventory, itemId);
    }

    @Override
    public void deleteInventoryById(UUID inventoryId) {
        inventoryRepository.deleteById(inventoryId);
    }

    @SneakyThrows
    private Inventory getInventory(UUID volunteerId, UUID groupId) {
        if(groupId == null)
            return getVolunteerInventory(volunteerId);
        return getGroupInventory(volunteerId, groupId);
    }

    @SneakyThrows
    private Inventory getVolunteerInventory(UUID volunteerId) {
        Volunteer volunteer = volunteerService.getVolunteer(volunteerId);
        return volunteer.getInventory();
    }

    @SneakyThrows
    private Inventory getGroupInventory(UUID volunteerId, UUID groupId) {
        Group group = groupService.eitherIsAGroupModerator(volunteerId, groupId);
        return group.getInventory();
    }

}
