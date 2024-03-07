package com.application.volunteers.item.model;


public enum ItemMeasurement {
    KILOGRAMS, LITRE, UNITS, ANOTHER;

    public static ItemMeasurement valueOfChecked(String value){
        if(value == null)
            throw new IllegalArgumentException("Item measurement is null");
        return valueOf(value);
    }
}
