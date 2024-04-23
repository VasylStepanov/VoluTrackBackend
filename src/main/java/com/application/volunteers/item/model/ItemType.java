package com.application.volunteers.item.model;

import lombok.SneakyThrows;

public enum ItemType {
    ELECTRONIC_STUFF, CLOTHES, FOOD, MEDICINE, ANOTHER;

    @SneakyThrows
    public static ItemType valueOfChecked(String value) {
        try {
            return valueOf(value);
        } catch (Exception e) {
            throw new RuntimeException("Item type unknown");
        }
    }

    @SneakyThrows
    public static ItemType valueOfCheckedFull(String value) {
        if (value == null)
            throw new IllegalArgumentException("Item measurement is null");
        return valueOfChecked(value);
    }
}
