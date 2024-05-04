package com.application.volunteers.request.service;

import com.application.volunteers.inventory.model.Inventory;
import com.application.volunteers.item.dto.RequestItemDto;
import com.application.volunteers.item.dto.ResponseItemDto;
import com.application.volunteers.item.model.ItemMeasurement;
import com.application.volunteers.item.model.ItemType;
import com.application.volunteers.request.model.Request;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface RequestService {

    List<ResponseItemDto> findAllRequestItems(Request request);

    List<ResponseItemDto> findAllRequestItems(UUID volunteerId, UUID groupId);

    void saveRequestItem(RequestItemDto requestItemDto, UUID volunteerId, UUID groupId);

    void updateRequestItem(RequestItemDto requestItemDto, UUID volunteerId, UUID groupId, UUID itemId);

    void deleteRequestItem(UUID volunteerId, UUID groupId, UUID itemId);

    void deleteRequestById(UUID requestId);
}
