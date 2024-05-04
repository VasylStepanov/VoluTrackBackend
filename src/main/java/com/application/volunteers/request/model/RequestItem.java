package com.application.volunteers.request.model;

import com.application.config.BaseEntity;
import com.application.volunteers.item.model.Item;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Entity(name = "request_item")
@Table(name = "request_items", schema = "volunteer_data")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RequestItem extends BaseEntity {

    @OneToOne
    @JoinColumn(name = "item_id")
    Item item;

    @ManyToOne
    @JoinColumn(name = "request_id")
    Request request;
}
