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

    @Query("""
            SELECT r
            FROM request_item r
            WHERE ((r.request.group.address.coordinatesLongitude < ?1 AND
            r.request.group.address.coordinatesLongitude > ?2 AND
            r.request.group.address.coordinatesLatitude < ?3 AND
            r.request.group.address.coordinatesLatitude > ?4)
            OR (r.request.group.address.coordinatesLongitude < ?1 AND
            r.request.group.address.coordinatesLongitude > ?2 AND
            r.request.group.address.coordinatesLatitude < ?3 AND
            r.request.group.address.coordinatesLatitude > ?4))""")
    List<RequestItem> findAllByAddress(double longitudeX,
                                       double longitudeY,
                                       double latitudeX,
                                       double latitudeY);

    @Query("""
            SELECT r
            FROM request_item r
            WHERE r.itemType = ?5 AND
            ((r.request.group.address.coordinatesLongitude < ?1 AND
            r.request.group.address.coordinatesLongitude > ?2 AND
            r.request.group.address.coordinatesLatitude < ?3 AND
            r.request.group.address.coordinatesLatitude > ?4)
            OR (r.request.group.address.coordinatesLongitude < ?1 AND
            r.request.group.address.coordinatesLongitude > ?2 AND
            r.request.group.address.coordinatesLatitude < ?3 AND
            r.request.group.address.coordinatesLatitude > ?4))
            """)
    List<RequestItem> findAllByAddress(double longitudeX,
                                            double longitudeY,
                                            double latitudeX,
                                            double latitudeY,
                                            String itemType);

}
