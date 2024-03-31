package com.application.volunteers.item.repository;

import com.application.volunteers.item.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ItemRepository extends JpaRepository<Item, UUID> {
    List<Item> findByInventoryId(UUID inventoryId);
}
