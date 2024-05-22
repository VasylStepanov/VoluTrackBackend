package com.application.content.items.request.model;

import com.application.config.BaseEntity;
import com.application.content.items.item.model.ItemType;
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
@Table(name = "request_items", schema = "item_data")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RequestItem extends BaseEntity {

    @Column(name = "end_product", nullable = false)
    boolean endProduct;

    @Column(name = "weight", nullable = false)
    Double weight;

    @Column(name = "amount", nullable = false)
    Integer amount;

    @Column(name = "item_type", nullable = false)
    @Enumerated(EnumType.ORDINAL)
    ItemType itemType;

    @ManyToOne
    @JoinColumn(name = "request_id")
    Request request;
}
