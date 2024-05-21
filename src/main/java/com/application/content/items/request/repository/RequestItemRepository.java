package com.application.content.items.request.repository;

import com.application.content.items.item.model.ItemType;
import com.application.content.items.request.model.RequestItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface RequestItemRepository extends JpaRepository<RequestItem, UUID> {

    List<RequestItem> findAllByRequestId(UUID requestId);

    List<RequestItem> findAllByItemType(ItemType itemType);

}
