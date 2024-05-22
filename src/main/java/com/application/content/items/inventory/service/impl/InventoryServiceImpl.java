package com.application.content.items.inventory.service.impl;

import com.application.content.general.address.model.Address;
import com.application.content.items.inventory.model.Inventory;
import com.application.content.items.inventory.model.InventoryItem;
import com.application.content.items.inventory.repository.InventoryItemRepository;
import com.application.content.items.inventory.repository.InventoryRepository;
import com.application.content.items.inventory.service.InventoryService;
import com.application.content.groups.group.model.Group;
import com.application.content.groups.group.service.GroupService;
import com.application.content.items.inventory.dto.InventoryItemDto;
import com.application.content.items.inventory.dto.ResponseInventoryItemDto;
import com.application.content.items.item.model.ItemType;
import com.application.content.items.item.service.ItemValidation;
import com.application.content.volunteers.volunteer.model.Volunteer;
import com.application.content.volunteers.volunteer.service.VolunteerService;
import lombok.Setter;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class InventoryServiceImpl implements InventoryService {

    @Setter
    @Value("${const.size_length}")
    double SIZE_LENGTH;

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
    public List<InventoryItem> findAllInventoryItemsByAddress(Address address) {
        return inventoryItemRepository.findAllByAddress(address.getCoordinatesLongitude() + SIZE_LENGTH,
                address.getCoordinatesLongitude() - SIZE_LENGTH,
                address.getCoordinatesLatitude() + SIZE_LENGTH,
                address.getCoordinatesLatitude() - SIZE_LENGTH);
    }

    @Override
    public List<InventoryItem> findAllInventoryItemsByAddressAndItemType(Address address, ItemType itemType){
        return inventoryItemRepository.findAllByAddress(address.getCoordinatesLongitude() + SIZE_LENGTH,
                address.getCoordinatesLongitude() - SIZE_LENGTH,
                address.getCoordinatesLatitude() + SIZE_LENGTH,
                address.getCoordinatesLatitude() - SIZE_LENGTH,
                itemType.name());
    }

    @Override
    public Volunteer getRepresentative(InventoryItem inventoryItem){
        if(inventoryItem.getInventory().getGroup() == null)
            return inventoryItem.getInventory().getVolunteer();
        return inventoryItem.getInventory().getGroup().getVolunteer();
    }

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
                .endProduct(inventoryItemDto.endProduct())
                .readyToSend(false)
                .name(itemValidation.eitherNameValidFull(inventoryItemDto.name()))
                .description(itemValidation.eitherDescriptionValid(inventoryItemDto.description()))
                .amount(itemValidation.eitherMoreThanZeroEqualFull(inventoryItemDto.amount()))
                .weight(itemValidation.eitherMoreThanZeroFull(inventoryItemDto.weight()))
                .itemType(inventoryItemDto.itemType())
                .inventory(inventory)
                .build();
        inventoryItemRepository.save(inventoryItem);
    }

    @Override
    @SneakyThrows
    @Transactional
    public void updateItem(InventoryItemDto inventoryItemDto, UUID volunteerId, UUID groupId, UUID inventoryItemId) {
        Inventory inventory = getInventory(volunteerId, groupId);
        InventoryItem inventoryItem = inventory.getInventoryItems().stream()
                .filter(x -> x.getId().equals(inventoryItemId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Item isn't from the inventory"));
        if(inventoryItemDto.endProduct() != null)
            inventoryItem.setEndProduct(inventoryItemDto.endProduct());
        if(inventoryItemDto.name() != null)
            inventoryItem.setName(itemValidation.eitherNameValid(inventoryItemDto.name()));
        if(inventoryItemDto.description() != null)
            inventoryItem.setDescription(inventoryItemDto.description());
        if(inventoryItemDto.amount() != null)
            inventoryItem.setAmount(itemValidation.eitherMoreThanZeroEqual(inventoryItemDto.amount()));
        if(inventoryItemDto.weight() != null)
            inventoryItem.setWeight(itemValidation.eitherMoreThanZero(inventoryItemDto.weight()));
        if(inventoryItemDto.itemType() != null)
            inventoryItem.setItemType(inventoryItemDto.itemType());
    }

    @Override
    @SneakyThrows
    @Transactional
    public void updateReadyToSend(boolean readyToSend, UUID volunteerId, UUID groupId, UUID inventoryItemId){
        Inventory inventory = getInventory(volunteerId, groupId);
        InventoryItem inventoryItem = inventory.getInventoryItems().stream()
                .filter(x -> x.getId().equals(inventoryItemId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Item isn't from the inventory"));
        if(inventoryItem.isReadyToSend() == readyToSend)
            throw new RuntimeException("Ready to send already set to: " + readyToSend);
        if(readyToSend)
            inventoryItem.setReadyToSend(true);
        else
            inventoryItem.setReadyToSend(false);
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
