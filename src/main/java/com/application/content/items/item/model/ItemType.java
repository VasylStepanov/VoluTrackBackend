package com.application.content.items.item.model;

import lombok.SneakyThrows;

public enum ItemType {
    ELECTRONIC_STUFF, CLOTHES, SHOES, FOOD, DRONES, DRONE_SPARES, CAMOUFLAGE_NETS, TRENCH_CANDLES, ANOTHER;

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
