package com.application.content.items.inventory.service.impl;

import com.application.content.items.inventory.model.Inventory;
import com.application.content.items.inventory.model.InventoryItem;
import com.application.content.items.inventory.repository.InventoryItemRepository;
import com.application.content.items.inventory.repository.InventoryRepository;
import com.application.content.items.inventory.service.InventoryService;
import com.application.content.groups.group.model.Group;
import com.application.content.groups.group.service.GroupService;
import com.application.content.items.item.dto.InventoryItemDto;
import com.application.content.items.item.dto.ResponseInventoryItemDto;
import com.application.content.items.item.service.ItemValidation;
import com.application.content.volunteers.volunteer.model.Volunteer;
import com.application.content.volunteers.volunteer.service.VolunteerService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class InventoryServiceImpl implements InventoryService {

    @Autowired
    VolunteerService volunteerService;

    @Autowired
    GroupService groupService;

    @Autowired
    InventoryRepository inventoryRepository;

    @Autowired
    InventoryItemRepository inventoryItemRepository;

    @Autowired
    ItemValidation itemValidation;

    @Override
    public List<ResponseInventoryItemDto> findAllItems(UUID volunteerId, UUID groupId) {
        Inventory inventory = getInventory(volunteerId, groupId);
        return inventoryItemRepository
                .findAllByInventoryId(inventory.getId())
                .stream()
                .map(ResponseInventoryItemDto::toResponseInventoryItemDto)
                .collect(Collectors.toList());
    }

    @Override
    @SneakyThrows
    public void saveItem(InventoryItemDto inventoryItemDto, UUID volunteerId, UUID groupId) {
        Inventory inventory = getInventory(volunteerId, groupId);
        InventoryItem inventoryItem = InventoryItem.builder()
                .name(itemValidation.eitherNameValidFull(inventoryItemDto.name()))
                .description(itemValidation.eitherDescriptionValid(inventoryItemDto.description()))
                .amount(itemValidation.eitherIntegerMoreThanZeroEqualFull(inventoryItemDto.amount()))
                .weight(itemValidation.eitherIntegerMoreThanZeroFull(inventoryItemDto.weight()))
                .itemType(inventoryItemDto.itemType())
                .inventory(inventory)
                .build();
        inventoryItemRepository.save(inventoryItem);
    }

    @Override
    @SneakyThrows
    public void updateItem(InventoryItemDto inventoryItemDto, UUID volunteerId, UUID groupId, UUID inventoryItemId) {
        Inventory inventory = getInventory(volunteerId, groupId);
        InventoryItem inventoryItem = inventory.getInventoryItems().stream()
                .filter(x -> x.getId().equals(inventoryItemId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Item isn't from the inventory"));
        if(inventoryItemDto.name() != null)
            inventoryItem.setName(itemValidation.eitherNameValid(inventoryItemDto.name()));
        if(inventoryItemDto.description() != null)
            inventoryItem.setDescription(inventoryItemDto.description());
        if(inventoryItemDto.amount() != null)
            inventoryItem.setAmount(itemValidation.eitherIntegerMoreThanZeroEqual(inventoryItemDto.amount()));
        if(inventoryItemDto.weight() != null)
            inventoryItem.setWeight(itemValidation.eitherIntegerMoreThanZero(inventoryItemDto.weight()));
        if(inventoryItemDto.itemType() != null)
            inventoryItem.setItemType(inventoryItemDto.itemType());
    }

    @Override
    public void deleteItem(UUID volunteerId, UUID groupId, UUID inventoryItemId) {
        Inventory inventory = getInventory(volunteerId, groupId);
        InventoryItem inventoryItem = inventory.getInventoryItems().stream()
                .filter(x -> x.getId().equals(inventoryItemId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Item isn't from the inventory"));
        inventoryItemRepository.delete(inventoryItem);
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
