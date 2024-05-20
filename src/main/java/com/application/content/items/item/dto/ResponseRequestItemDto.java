package com.application.content.items.item.dto;

import com.application.content.items.item.model.ItemType;
import com.application.content.items.request.model.RequestItem;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ResponseRequestItemDto {

    Integer weight;

    Integer amount;

    ItemType itemType;

    public static ResponseRequestItemDto toResponseRequestItemDto(RequestItem requestItem){
        return new ResponseRequestItemDto(requestItem.getWeight(),
                requestItem.getAmount(),
                requestItem.getItemType());
    }
}
