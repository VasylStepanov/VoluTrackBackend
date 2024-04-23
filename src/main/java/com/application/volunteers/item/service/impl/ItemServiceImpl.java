package com.application.volunteers.item.service.impl;

import com.application.user.model.User;
import com.application.user.service.UserService;
import com.application.volunteers.group.model.Group;
import com.application.volunteers.group.service.GroupService;
import com.application.volunteers.inventory.model.Inventory;
import com.application.volunteers.item.dto.RequestItemDto;
import com.application.volunteers.item.dto.ResponseItemDto;
import com.application.volunteers.item.model.Item;
import com.application.volunteers.item.model.ItemMeasurement;
import com.application.volunteers.item.model.ItemType;
import com.application.volunteers.item.repository.ItemRepository;
import com.application.volunteers.item.service.ItemService;
import com.application.volunteers.volunteer.model.Volunteer;
import com.application.volunteers.volunteer.service.VolunteerService;
import lombok.AccessLevel;
import lombok.SneakyThrows;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ItemServiceImpl implements ItemService {

    @Autowired
    ItemRepository itemRepository;

    @Autowired
    ItemValidation itemValidation;

    @Override
    public List<ResponseItemDto> findAllItems(UUID inventoryId) {
        return itemRepository
                .findByInventoryId(inventoryId)
                .stream()
                .map(ResponseItemDto::toResponseItemDto)
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    @SneakyThrows
    public void saveItem(RequestItemDto requestItemDto, Inventory inventory) {
        itemRepository.save(Item.builder()
            .name(itemValidation.eitherNameValidFull(requestItemDto.name()))
            .description(itemValidation.eitherDescriptionValid(requestItemDto.description()))
            .amount(itemValidation.eitherAmountValidFull(requestItemDto.amount()))
            .itemMeasurement(ItemMeasurement.valueOfCheckedFull(requestItemDto.itemMeasurement()))
            .itemType(ItemType.valueOfCheckedFull(requestItemDto.itemType()))
            .inventory(inventory)
            .build());
    }

    @Transactional
    @Override
    @SneakyThrows
    public void updateItem(RequestItemDto requestItemDto, UUID inventoryId, UUID itemId) {
        Item item = eitherItemOwner(inventoryId, itemId);

        if(requestItemDto.name() != null)
            item.setName(itemValidation.eitherNameValid(requestItemDto.name()));
        if(requestItemDto.description() != null)
            item.setDescription(itemValidation.eitherDescriptionValid(requestItemDto.description()));
        if(requestItemDto.amount() != null)
            item.setAmount(itemValidation.eitherAmountValid(requestItemDto.amount()));
        if(requestItemDto.itemMeasurement() != null)
            item.setItemMeasurement(ItemMeasurement.valueOfChecked(requestItemDto.itemMeasurement()));
        if(requestItemDto.itemType() != null)
            item.setItemType(ItemType.valueOfChecked(requestItemDto.itemType()));
    }

    @Transactional
    @Override
    @SneakyThrows
    public void deleteItem(UUID inventoryId, UUID itemId) {
        eitherItemOwner(inventoryId, itemId);
        itemRepository.deleteById(itemId);
    }

    @SneakyThrows
    private Item getItem(UUID itemId){
        return itemRepository.findById(itemId)
                .orElseThrow(() -> new RuntimeException("Item with this id not found"));
    }

    @SneakyThrows
    private Item eitherItemOwner(UUID inventoryId, UUID itemId){
        Item item = getItem(itemId);
        if(item.getInventory().getId().equals(inventoryId))
            return item;
        throw new RuntimeException("User doesn't own this item");
    }

}
