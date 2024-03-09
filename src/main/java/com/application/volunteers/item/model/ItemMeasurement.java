package com.application.volunteers.item.model;


import lombok.SneakyThrows;

public enum ItemMeasurement {
    KILOGRAMS, LITRE, UNITS, ANOTHER;

    @SneakyThrows
    public static ItemMeasurement valueOfChecked(String value){
        try {
            return valueOf(value);
        } catch (Exception e) {
            throw new RuntimeException("Item measurement unknown");
        }
    }

    @SneakyThrows
    public static ItemMeasurement valueOfCheckedFull(String value){
        if(value == null)
            throw new IllegalArgumentException("Item measurement is null");
        return valueOfChecked(value);
    }
}
