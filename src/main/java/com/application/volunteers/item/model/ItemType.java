package com.application.volunteers.item.model;

public enum ItemType {
    ELECTRONIC_STUFF, CLOTHES, FOOD, MEDICINE, ANOTHER;

    public static ItemType valueOfChecked(String value){
        if(value == null)
            throw new IllegalArgumentException("Item measurement is null");
        return valueOf(value);
    }

}
