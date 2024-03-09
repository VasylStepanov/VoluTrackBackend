package com.application.volunteers.item.model;

import com.application.config.BaseEntity;
import com.application.volunteers.volunteer.model.Volunteer;
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
@Table(name = "items", schema = "volunteer_data")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Item extends BaseEntity {

    @Column(name = "name", length = 64, nullable = false)
    String name;

    @Column(name = "description", length = 512, nullable = false)
    String description;

    @Column(name = "amount", nullable = false)
    Integer amount;

    @Setter(AccessLevel.NONE)
    @OneToOne
    @JoinColumn(name = "volunteer_id", nullable = false)
    Volunteer volunteer;

    @Column(name = "item_type", nullable = false)
    @Enumerated(EnumType.ORDINAL)
    ItemType itemType;

    @Column(name = "item_measurement", nullable = false)
    @Enumerated(EnumType.ORDINAL)
    ItemMeasurement itemMeasurement;
}