package com.application.volunteers.request.service.impl;

import com.application.volunteers.group.model.Group;
import com.application.volunteers.group.service.GroupService;
import com.application.volunteers.inventory.model.Inventory;
import com.application.volunteers.item.dto.RequestItemDto;
import com.application.volunteers.item.dto.ResponseItemDto;
import com.application.volunteers.item.model.Item;
import com.application.volunteers.item.service.ItemService;
import com.application.volunteers.request.model.Request;
import com.application.volunteers.request.model.RequestItem;
import com.application.volunteers.request.repository.RequestItemRepository;
import com.application.volunteers.request.repository.RequestRepository;
import com.application.volunteers.request.service.RequestService;
import com.application.volunteers.volunteer.model.Volunteer;
import com.application.volunteers.volunteer.service.VolunteerService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class RequestServiceImpl implements RequestService {

    @Autowired
    VolunteerService volunteerService;

    @Autowired
    GroupService groupService;

    @Autowired
    ItemService itemService;

    @Autowired
    RequestRepository requestRepository;

    @Autowired
    RequestItemRepository requestItemRepository;

    @Override
    public List<ResponseItemDto> findAllRequestItems(Request request) {
        return itemService.getItems(request);
    }

    @Override
    public List<ResponseItemDto> findAllRequestItems(UUID volunteerId, UUID groupId) {
        Request request = getRequest(volunteerId, groupId);
        return findAllRequestItems(request);
    }

    @Override
    public void saveRequestItem(RequestItemDto requestItemDto, UUID volunteerId, UUID groupId) {
        Request request = getRequest(volunteerId, groupId);
        Item item = itemService.saveItem(requestItemDto);
        RequestItem requestItem = RequestItem.builder()
                .request(request)
                .item(item)
                .build();
        requestItemRepository.save(requestItem);
    }

    @Override
    public void updateRequestItem(RequestItemDto requestItemDto, UUID volunteerId, UUID groupId, UUID itemId) {
        Request request = getRequest(volunteerId, groupId);
        itemService.updateItem(requestItemDto, request, itemId);
    }

    @Override
    public void deleteRequestItem(UUID volunteerId, UUID groupId, UUID itemId) {
        Request request = getRequest(volunteerId, groupId);
        itemService.deleteItem(request, itemId);
    }

    @Override
    public void deleteRequestById(UUID requestId) {
        requestRepository.deleteById(requestId);
    }

    @SneakyThrows
    private Request getRequest(UUID volunteerId, UUID groupId) {
        if(groupId == null)
            return getVolunteerRequest(volunteerId);
        return getGroupRequest(volunteerId, groupId);
    }

    @SneakyThrows
    private Request getVolunteerRequest(UUID volunteerId) {
        Volunteer volunteer = volunteerService.getVolunteer(volunteerId);
        return volunteer.getRequest();
    }

    @SneakyThrows
    private Request getGroupRequest(UUID volunteerId, UUID groupId) {
        Group group = groupService.eitherIsAGroupRepresent(volunteerId, groupId);
        return group.getRequest();
    }
}
