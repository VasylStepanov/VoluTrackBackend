package com.application.content.items.inventory.model;

import com.application.config.BaseEntity;
import com.application.content.items.item.model.ItemType;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Entity(name = "inventory_item")
@Table(name = "inventory_items", schema = "item_data")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class InventoryItem extends BaseEntity {

    @Column(name = "ready_to_send")
    boolean readyToSend;

    @Column(name = "end_product", nullable = false)
    boolean endProduct;

    @Column(name = "name", length = 64, nullable = false)
    String name;

    @Column(name = "description", length = 512, nullable = false)
    String description;

    @Column(name = "weight", nullable = false)
    Double weight;

    @Column(name = "amount", nullable = false)
    Integer amount;

    @Column(name = "item_type", nullable = false)
    @Enumerated(EnumType.ORDINAL)
    ItemType itemType;

    @ManyToOne
    @JoinColumn(name = "inventory_id")
    Inventory inventory;
}
