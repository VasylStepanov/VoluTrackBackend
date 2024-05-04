package com.application.content.items.item.service.impl;

import com.application.content.items.item.dto.RequestItemDto;
import com.application.content.items.item.dto.ResponseItemDto;
import com.application.content.items.item.model.ItemMeasurement;
import com.application.content.items.inventory.model.Inventory;
import com.application.content.items.inventory.model.InventoryItem;
import com.application.content.items.item.model.Item;
import com.application.content.items.item.model.ItemType;
import com.application.content.items.item.repository.ItemRepository;
import com.application.content.items.item.service.ItemService;
import com.application.content.items.request.model.Request;
import com.application.content.items.request.model.RequestItem;
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
    public List<ResponseItemDto> getItems(Inventory inventory){
        return inventory.getInventoryItems()
            .stream()
            .map(InventoryItem::getItem)
            .map(ResponseItemDto::toResponseItemDto)
            .collect(Collectors.toList());
    }

    @Override
    public List<ResponseItemDto> getItems(Request request){
        return request.getRequestItems()
                .stream()
                .map(RequestItem::getItem)
                .map(ResponseItemDto::toResponseItemDto)
                .collect(Collectors.toList());
    }

    @Override
    public Set<ItemType> findAllItemTypes(){
        return Set.of(ItemType.values());
    }

    @Override
    public Set<ItemMeasurement> findAllItemMeasurements() {
        return Set.of(ItemMeasurement.values());
    }

    @Transactional
    @Override
    @SneakyThrows
    public Item saveItem(RequestItemDto requestItemDto) {
        return itemRepository.save(Item.builder()
            .name(itemValidation.eitherNameValidFull(requestItemDto.name()))
            .description(itemValidation.eitherDescriptionValid(requestItemDto.description()))
            .amount(itemValidation.eitherAmountValidFull(requestItemDto.amount()))
            .itemMeasurement(ItemMeasurement.valueOfCheckedFull(requestItemDto.itemMeasurement()))
            .itemType(ItemType.valueOfCheckedFull(requestItemDto.itemType()))
            .build());
    }

    @Transactional
    @Override
    @SneakyThrows
    public void updateItem(RequestItemDto requestItemDto, Inventory inventory, UUID itemId) {
        Item item = eitherItemIsFromInventory(inventory, itemId);
        updateItem(requestItemDto, item);
    }

    @Transactional
    @Override
    @SneakyThrows
    public void updateItem(RequestItemDto requestItemDto, Request request, UUID itemId) {
        Item item = eitherItemIsFromRequest(request, itemId);
        updateItem(requestItemDto, item);
    }

    @Transactional
    @Override
    @SneakyThrows
    public void deleteItem(Inventory inventory, UUID itemId) {
        Item item = eitherItemIsFromInventory(inventory, itemId);
        itemRepository.delete(item);
    }

    @Transactional
    @Override
    @SneakyThrows
    public void deleteItem(Request request, UUID itemId) {
        Item item = eitherItemIsFromRequest(request, itemId);
        itemRepository.delete(item);
    }

    @SneakyThrows
    private Item eitherItemIsFromInventory(Inventory inventory, UUID itemId){
        return inventory.getInventoryItems()
                .stream()
                .map(InventoryItem::getItem)
                .filter(x -> x.getId().equals(itemId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Item isn't from the inventory"));
    }

    @SneakyThrows
    private Item eitherItemIsFromRequest(Request request, UUID itemId){
        return request.getRequestItems()
                .stream()
                .map(RequestItem::getItem)
                .filter(x -> x.getId().equals(itemId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Item isn't from the request list"));
    }

    private void updateItem(RequestItemDto requestItemDto, Item item){
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
}
