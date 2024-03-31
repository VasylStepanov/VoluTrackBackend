package com.application.volunteers.group.model;

import com.application.config.BaseEntity;
import com.application.volunteers.address.model.Address;
import com.application.volunteers.address.model.IAddress;
import com.application.volunteers.inventory.model.Inventory;
import com.application.volunteers.volunteer.model.Volunteer;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

@Setter
@Getter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "group")
@Table(name = "groups", schema = "volunteer_data")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Group extends BaseEntity implements IAddress {

    @Column(name = "name", nullable = false)
    String name;

    @Column(name = "description")
    String description;

    @Builder.Default
    @Column(name = "help_counter")
    int helpCounter = 0;

    @ManyToOne
    @JoinColumn(name = "volunteer_id", nullable = false)
    Volunteer volunteer;

    @OneToOne
    @JoinColumn(name = "address_id")
    Address address;

    @OneToOne
    @JoinColumn(name = "inventory_id")
    Inventory inventory;
}