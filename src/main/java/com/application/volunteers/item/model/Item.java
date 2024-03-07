package com.application.volunteers.item.model;

import com.application.volunteers.volunteer.model.Volunteer;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.UUID;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name = "item")
@Table(name = "items", schema = "volunteer_data")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID id;

    @Column(name = "name", length = 64, nullable = false)
    String name;

    @Column(name = "description", length = 256, nullable = false)
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