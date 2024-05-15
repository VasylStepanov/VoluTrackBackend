package com.application.content.groups.member.entity;

import com.application.config.BaseEntity;
import com.application.content.groups.group.model.Group;
import com.application.content.volunteers.volunteer.model.Volunteer;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "member")
@Table(name = "members", schema = "group_data", uniqueConstraints = @UniqueConstraint(name = "uk_volunteer_and_group",
        columnNames = {"volunteer_id", "group_id"}))
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Member extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "volunteer_id", nullable = false)
    Volunteer volunteer;

    @ManyToOne
    @JoinColumn(name = "group_id", nullable = false)
    Group group;

    @Column(name = "member_role", nullable = false)
    @Enumerated(EnumType.ORDINAL)
    MemberRole memberRole;
}
