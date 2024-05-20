package com.application.content.items.request.service;

import com.application.content.items.item.dto.RequestItemDto;
import com.application.content.items.item.dto.ResponseRequestItemDto;

import java.util.List;
import java.util.UUID;

public interface RequestService {

    List<ResponseRequestItemDto> findAllRequestItems(UUID volunteerId, UUID groupId);

    void saveRequestItem(RequestItemDto requestItemDto, UUID volunteerId, UUID groupId);

    void updateRequestItem(RequestItemDto requestItemDto, UUID volunteerId, UUID groupId, UUID itemId);

    void deleteRequestItem(UUID volunteerId, UUID groupId, UUID itemId);

    void deleteRequestById(UUID requestId);
}
