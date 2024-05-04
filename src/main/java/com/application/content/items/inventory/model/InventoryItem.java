package com.application.content.items.inventory.model;

import com.application.config.BaseEntity;
import com.application.content.items.item.model.Item;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Entity(name = "inventory_items")
@Table(name = "inventory_items", schema = "item_data")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class InventoryItem extends BaseEntity {

    @OneToOne
    @JoinColumn(name = "item_id")
    Item item;

    @ManyToOne
    @JoinColumn(name = "inventory_id")
    Inventory inventory;
}
