package com.application.content.items.request.model;

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
@Entity(name = "request")
@Table(name = "requests", schema = "item_data")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Request extends BaseEntity {

    @OneToMany(mappedBy = "request")
    List<RequestItem> requestItems;

    @OneToOne(mappedBy = "request", optional = false, fetch = FetchType.LAZY)
    Group group;

    @OneToOne(mappedBy = "request", optional = false, fetch = FetchType.LAZY)
    Volunteer volunteer;
}
