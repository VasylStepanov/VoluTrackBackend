package com.application.content.items.item.model;

import com.application.config.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Entity(name = "item")
@Table(name = "items", schema = "item_data")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Item extends BaseEntity {

    @Column(name = "name", length = 64, nullable = false)
    String name;

    @Column(name = "description", length = 512, nullable = false)
    String description;

    @Column(name = "amount", nullable = false)
    Integer amount;

    @Column(name = "item_type", nullable = false)
    @Enumerated(EnumType.ORDINAL)
    ItemType itemType;

    @Column(name = "item_measurement", nullable = false)
    @Enumerated(EnumType.ORDINAL)
    ItemMeasurement itemMeasurement;
}