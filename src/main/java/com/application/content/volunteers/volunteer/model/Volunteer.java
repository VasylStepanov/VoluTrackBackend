package com.application.content.volunteers.volunteer.model;

import com.application.config.BaseEntity;
import com.application.content.address.model.Address;
import com.application.content.address.model.IAddress;
import com.application.content.groups.member.entity.Member;
import com.application.content.items.inventory.model.Inventory;
import com.application.content.volunteers.car.model.Car;
import com.application.user.model.User;
import com.application.content.groups.group.model.Group;
import com.application.content.items.request.model.Request;
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
@Entity(name = "volunteer")
@Table(name = "volunteers", schema = "volunteer_data", uniqueConstraints = {
    @UniqueConstraint(name = "uk_volunteer_user_id", columnNames = {"user_id"})
})
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Volunteer extends BaseEntity implements IAddress {

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

    @OneToOne
    @JoinColumn(name = "request_id")
    Request request;

    @OneToMany(mappedBy = "volunteer", fetch = FetchType.LAZY)
    List<Member> memberInGroups;
}
