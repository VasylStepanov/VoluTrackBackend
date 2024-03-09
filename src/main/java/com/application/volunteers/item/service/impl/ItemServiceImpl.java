package com.application.volunteers.item.service.impl;

import com.application.user.model.User;
import com.application.user.service.UserService;
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

    @Autowired
    VolunteerService volunteerService;

    @Autowired
    UserService userService;

    @Override
    public List<ResponseItemDto> findAllItems(UUID volunteerId) {
        return itemRepository
                .findByVolunteerId(volunteerId)
                .stream()
                .map(ResponseItemDto::toResponseItemDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<ResponseItemDto> findAllItemsByUserEmail(String email) {
        User user = userService.findUserByEmail(email);
        return findAllItems(user.getVolunteer().getId());
    }

    @Override
    @SneakyThrows
    public ResponseItemDto findItemById(UUID itemId) {
        return ResponseItemDto.toResponseItemDto(getItem(itemId));
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
    public void saveItem(UUID volunteerId, RequestItemDto requestItemDto) {
        itemRepository.save(Item.builder()
            .name(itemValidation.eitherNameValidFull(requestItemDto.name()))
            .description(itemValidation.eitherDescriptionValid(requestItemDto.description()))
            .amount(itemValidation.eitherAmountValidFull(requestItemDto.amount()))
            .itemMeasurement(ItemMeasurement.valueOfCheckedFull(requestItemDto.itemMeasurement()))
            .itemType(ItemType.valueOfCheckedFull(requestItemDto.itemType()))
            .volunteer(volunteerService.getVolunteer(volunteerId))
            .build());
    }

    @Transactional
    @Override
    @SneakyThrows
    public void updateItem(UUID volunteerId, UUID itemId, RequestItemDto requestItemDto) {
        Item item = getItem(itemId);
        if(item.getVolunteer().getId().equals(volunteerId)) {
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
        } else
            throw new RuntimeException("User doesn't own this item");
    }

    @Transactional
    @Override
    @SneakyThrows
    public void deleteItemById(UUID volunteerId, UUID itemId) {
        if(getItem(itemId).getVolunteer().getId().equals(volunteerId))
            itemRepository.deleteById(itemId);
        else
            throw new RuntimeException("User doesn't own this item");
    }

    @SneakyThrows
    private Item getItem(UUID itemId){
        return itemRepository.findById(itemId)
                .orElseThrow(() -> new RuntimeException("Item with this id not found"));
    }

}
