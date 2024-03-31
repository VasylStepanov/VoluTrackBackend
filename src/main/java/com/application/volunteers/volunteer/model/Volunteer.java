package com.application.volunteers.volunteer.model;

import com.application.config.BaseEntity;
import com.application.user.model.User;
import com.application.volunteers.address.model.Address;
import com.application.volunteers.address.model.IAddress;
import com.application.volunteers.car.model.Car;
import com.application.volunteers.group.model.Group;
import com.application.volunteers.inventory.model.Inventory;
import com.application.volunteers.item.model.Item;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Entity(name = "volunteer")
@Table(name = "volunteers", schema = "volunteer_data", uniqueConstraints = {
    @UniqueConstraint(name = "uk_volunteer_user_id", columnNames = {"user_id"})
})
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Volunteer extends BaseEntity implements IAddress {

    @Column(name = "description")
    String description;

    @Builder.Default
    @Column(name = "help_counter")
    int helpCounter = 0;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    User user;

    @OneToOne
    @JoinColumn(name = "address_id")
    Address address;

    @OneToMany(mappedBy = "volunteer", fetch = FetchType.LAZY)
    List<Car> carList;

    @OneToMany(mappedBy = "volunteer", fetch = FetchType.LAZY)
    List<Group> groupList;

    @OneToOne
    @JoinColumn(name = "inventory_id")
    Inventory inventory;
}
