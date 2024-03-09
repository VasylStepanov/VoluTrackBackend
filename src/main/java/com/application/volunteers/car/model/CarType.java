package com.application.volunteers.car.model;


public enum CarType {
    SEDAN_CAR, HATCHBACK_CAR, SUV, TRUCK, VAN;

    public static CarType valueOfChecked(String value) {
        try {
            return valueOf(value);
        } catch (Exception e) {
            throw new RuntimeException("Car type unknown");
        }
    }

    public static CarType valueOfCheckedFull(String value) {
        if (value == null)
            throw new IllegalArgumentException("Car type is null");
        return valueOfChecked(value);
    }
}
