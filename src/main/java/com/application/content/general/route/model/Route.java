package com.application.content.general.route.model;

import com.application.config.BaseEntity;
import com.application.content.general.address.model.Address;
import com.application.content.groups.group.model.Group;
import com.application.content.items.inventory.model.InventoryItem;
import com.application.content.items.request.model.RequestItem;
import com.application.content.volunteers.car.model.Car;
import com.application.content.volunteers.volunteer.model.Volunteer;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Entity(name = "route")
@Table(name = "routes", schema = "general_data")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Route extends BaseEntity {

    @Column(name = "start_at", nullable = false)
    LocalDateTime startAt;

    @Column(name = "status", nullable = false)
    RouteStatus routeStatus;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "driver_id", nullable = false)
    Volunteer driver;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "car_id", nullable = false)
    Car car;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "from_address_id", nullable = false)
    Address fromAddress;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "to_address_id", nullable = false)
    Address toAddress;

    @OneToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "inventory_item_id")
    InventoryItem inventoryItem;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "request_item_id")
    RequestItem requestItem;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "volunteer_giver_id")
    Volunteer volunteerGiver;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "volunteer_taker_id")
    Volunteer volunteerTaker;
}
