package com.application.content.items.inventory.repository;

import com.application.content.items.inventory.model.InventoryItem;
import com.application.content.items.item.model.ItemType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface InventoryItemRepository extends JpaRepository<InventoryItem, UUID> {

    List<InventoryItem> findAllByInventoryId(UUID inventoryId);

    List<InventoryItem> findAllByItemType(ItemType itemType);


    @Query("""
            SELECT i
            FROM inventory_item i
            WHERE i.readyToSend = true
            AND ((i.inventory.group.address.coordinatesLongitude < ?1 AND
            i.inventory.group.address.coordinatesLongitude > ?2 AND
            i.inventory.group.address.coordinatesLatitude < ?3 AND
            i.inventory.group.address.coordinatesLatitude > ?4)
            OR (i.inventory.group.address.coordinatesLongitude < ?1 AND
            i.inventory.group.address.coordinatesLongitude > ?2 AND
            i.inventory.group.address.coordinatesLatitude < ?3 AND
            i.inventory.group.address.coordinatesLatitude > ?4))
            """)
    List<InventoryItem> findAllByAddress(double longitudeX,
                                         double longitudeY,
                                         double latitudeX,
                                         double latitudeY);

    @Query("""
            SELECT i
            FROM inventory_item i
            WHERE i.readyToSend = true AND i.itemType = ?5
            AND ((i.inventory.group.address.coordinatesLongitude < ?1 AND
            i.inventory.group.address.coordinatesLongitude > ?2 AND
            i.inventory.group.address.coordinatesLatitude < ?3 AND
            i.inventory.group.address.coordinatesLatitude > ?4)
            OR (i.inventory.group.address.coordinatesLongitude < ?1 AND
            i.inventory.group.address.coordinatesLongitude > ?2 AND
            i.inventory.group.address.coordinatesLatitude < ?3 AND
            i.inventory.group.address.coordinatesLatitude > ?4))
            """)
    List<InventoryItem> findAllByAddress(double longitudeX,
                                       double longitudeY,
                                       double latitudeX,
                                       double latitudeY,
                                       String itemType);
}
