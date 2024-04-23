package com.application.volunteers.inventory.service.impl;

import com.application.volunteers.group.model.Group;
import com.application.volunteers.group.service.GroupService;
import com.application.volunteers.inventory.model.Inventory;
import com.application.volunteers.inventory.repository.InventoryRepository;
import com.application.volunteers.inventory.service.InventoryService;
import com.application.volunteers.item.dto.RequestItemDto;
import com.application.volunteers.item.dto.ResponseItemDto;
import com.application.volunteers.item.model.ItemMeasurement;
import com.application.volunteers.item.model.ItemType;
import com.application.volunteers.item.service.ItemService;
import com.application.volunteers.volunteer.model.Volunteer;
import com.application.volunteers.volunteer.service.VolunteerService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    @Override
    public List<ResponseItemDto> findAllItems(Inventory inventory) {
        return itemService.findAllItems(inventory.getId());
    }

    @Override
    public List<ResponseItemDto> findAllItemsByVolunteerId(UUID volunteerId) {
        Inventory inventory = getVolunteerInventory(volunteerId);
        return findAllItems(inventory);
    }

    @Override
    public List<ResponseItemDto> findAllItemsByGroupId(UUID groupId) {
        Inventory inventory = groupService.getGroup(groupId).getInventory();
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
    public void saveItemByVolunteerId(RequestItemDto requestItemDto, UUID volunteerId) {
        Inventory inventory = getVolunteerInventory(volunteerId);
        itemService.saveItem(requestItemDto, inventory);
    }

    @Override
    @SneakyThrows
    public void saveItemByGroupId(RequestItemDto requestItemDto, UUID volunteerId, UUID groupId) {
        Inventory inventory = getGroupInventory(volunteerId, groupId);
        itemService.saveItem(requestItemDto, inventory);
    }

    @Override
    @SneakyThrows
    public void updateItemByVolunteerId(RequestItemDto requestItemDto, UUID volunteerId, UUID itemId) {
        Inventory inventory = getVolunteerInventory(volunteerId);
        itemService.updateItem(requestItemDto, inventory.getId(), itemId);
    }

    @Override
    @SneakyThrows
    public void updateItemByGroupId(RequestItemDto requestItemDto,  UUID volunteerId, UUID groupId, UUID itemId) {
        Inventory inventory = getGroupInventory(volunteerId, groupId);
        itemService.updateItem(requestItemDto, inventory.getId(), itemId);
    }

    @Override
    public void deleteItemByVolunteerId(UUID volunteerId, UUID itemId) {
        Inventory inventory = getVolunteerInventory(volunteerId);
        itemService.deleteItem(inventory.getId(), itemId);
    }

    @Override
    public void deleteItemByGroupId(UUID volunteerId, UUID groupId, UUID itemId) {
        Inventory inventory = getGroupInventory(volunteerId, groupId);
        itemService.deleteItem(inventory.getId(), itemId);
    }

    @Override
    public void deleteInventoryById(UUID inventoryId) {
        inventoryRepository.deleteById(inventoryId);
    }

    @SneakyThrows
    private Inventory getVolunteerInventory(UUID volunteerId){
        Volunteer volunteer = volunteerService.getVolunteer(volunteerId);
        return volunteer.getInventory();
    }

    @SneakyThrows
    private Inventory getGroupInventory(UUID volunteerId, UUID groupId){
        Group group = groupService.getGroup(groupId);
        if(group.getVolunteer().getId().equals(volunteerId))
            return group.getInventory();
        else
            throw new RuntimeException("User can't manage the group's items");
    }
}
