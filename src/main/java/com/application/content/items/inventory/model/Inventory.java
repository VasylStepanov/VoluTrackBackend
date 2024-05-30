package com.application.content.items.inventory.model;

import com.application.config.BaseEntity;
import com.application.content.groups.group.model.Group;
import com.application.content.volunteers.volunteer.model.Volunteer;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Entity(name = "inventory")
@Table(name = "inventories", schema = "item_data")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Inventory extends BaseEntity {

    @OneToMany(mappedBy = "inventory", fetch = FetchType.LAZY)
    List<InventoryItem> inventoryItems;

    @OneToOne(mappedBy = "inventory", optional = false, fetch = FetchType.LAZY)
    Group group;

    @OneToOne(mappedBy = "inventory", optional = false, fetch = FetchType.LAZY)
    Volunteer volunteer;
}
