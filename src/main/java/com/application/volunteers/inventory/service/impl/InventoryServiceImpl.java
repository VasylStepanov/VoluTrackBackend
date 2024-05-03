package com.application.volunteers.inventory.service.impl;

import com.application.volunteers.group.model.Group;
import com.application.volunteers.group.service.GroupService;
import com.application.volunteers.inventory.model.Inventory;
import com.application.volunteers.inventory.model.InventoryItem;
import com.application.volunteers.inventory.repository.InventoryItemRepository;
import com.application.volunteers.inventory.repository.InventoryRepository;
import com.application.volunteers.inventory.service.InventoryService;
import com.application.volunteers.item.dto.RequestItemDto;
import com.application.volunteers.item.dto.ResponseItemDto;
import com.application.volunteers.item.model.Item;
import com.application.volunteers.item.model.ItemMeasurement;
import com.application.volunteers.item.model.ItemType;
import com.application.volunteers.item.service.ItemService;
import com.application.volunteers.volunteer.model.Volunteer;
import com.application.volunteers.volunteer.service.VolunteerService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
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
        return itemService.getItemsFromInventory(inventory);
    }

    @Override
    public List<ResponseItemDto> findAllItems(UUID volunteerId, UUID groupId) {
        Inventory inventory = getInventory(volunteerId, groupId);
        return findAllItems(inventory);
    }

    @Override
    public Set<ItemType> findAllItemTypes(){
        return Set.of(ItemType.values());
    }

    @Override
    public Set<ItemMeasurement> findAllItemMeasurements() {
        return Set.of(ItemMeasurement.values());
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
        Group group = groupService.eitherIsAGroupRepresent(volunteerId, groupId);
        return group.getInventory();
    }

}
