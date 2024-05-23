package com.application.content.items.request.service;

import com.application.content.general.address.model.Address;
import com.application.content.items.item.model.ItemType;
import com.application.content.items.request.dto.RequestItemDto;
import com.application.content.items.request.dto.ResponseRequestItemDto;
import com.application.content.items.request.model.Request;
import com.application.content.items.request.model.RequestItem;
import com.application.content.volunteers.volunteer.model.Volunteer;

import java.util.List;
import java.util.UUID;

public interface RequestService {

    List<RequestItem> findAllRequestItemsByAddress(Address address);

    List<RequestItem> findAllRequestItemsByAddressAndItemType(Address address, ItemType itemType);

    Volunteer getRepresentative(RequestItem requestItem);

    List<ResponseRequestItemDto> findAllRequestItems(UUID volunteerId, UUID groupId);

    RequestItem saveRequestItem(RequestItemDto requestItemDto, UUID volunteerId, UUID groupId);

    void updateRequestItem(RequestItemDto requestItemDto, UUID volunteerId, UUID groupId, UUID itemId);

    void deleteRequestItem(UUID volunteerId, UUID groupId, UUID itemId);

    void deleteRequestById(UUID requestId);
}
