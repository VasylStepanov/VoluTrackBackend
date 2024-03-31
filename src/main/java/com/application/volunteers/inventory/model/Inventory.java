package com.application.volunteers.inventory.model;

import com.application.config.BaseEntity;
import com.application.volunteers.item.model.Item;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Entity(name = "inventory")
@Table(name = "inventories", schema = "volunteer_data")
public class Inventory extends BaseEntity {

    @OneToMany(mappedBy = "inventory")
    List<Item> items;
}
