package com.application.content.items.request.dto;

import com.application.content.items.item.model.ItemType;
import com.application.content.items.request.model.RequestItem;
import jakarta.persistence.Column;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ResponseRequestItemDto {

    RequestStatus requestStatus;

    boolean endProduct;

    Double weight;

    Integer amount;

    ItemType itemType;

    public static ResponseRequestItemDto toResponseRequestItemDto(RequestItem requestItem){
        return new ResponseRequestItemDto(
                requestItem.getRequestStatus(),
                requestItem.isEndProduct(),
                requestItem.getWeight(),
                requestItem.getAmount(),
                requestItem.getItemType());
    }
}
