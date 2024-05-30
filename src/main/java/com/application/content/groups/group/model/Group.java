package com.application.content.groups.group.model;

import com.application.config.BaseEntity;
import com.application.content.general.address.model.Address;
import com.application.content.general.address.model.IAddress;
import com.application.content.groups.member.entity.Member;
import com.application.content.items.inventory.model.Inventory;
import com.application.content.items.request.model.Request;
import com.application.content.volunteers.volunteer.model.Volunteer;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Setter
@Getter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "group")
@Table(name = "groups", schema = "group_data")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Group extends BaseEntity implements IAddress {

    @Column(name = "name", nullable = false)
    String name;

    @Column(name = "description")
    String description;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "volunteer_id", nullable = false)
    Volunteer volunteer;

    @OneToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "address_id")
    Address address;

    @OneToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "inventory_id")
    Inventory inventory;

    @OneToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "request_id")
    Request request;

    @OneToMany(mappedBy = "group", fetch = FetchType.LAZY)
    List<Member> membersInGroup;
}
