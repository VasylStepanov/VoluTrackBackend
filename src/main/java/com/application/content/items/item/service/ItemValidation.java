package com.application.content.items.item.service;

import lombok.SneakyThrows;

public interface ItemValidation {

    String eitherNameValid(String name);

    String eitherDescriptionValid(String description);

    Double eitherMoreThanZero(Double value);

    Integer eitherMoreThanZeroEqual(Integer value);

    Double eitherMoreThanZeroEqual(Double value);

    String eitherNameValidFull(String name);

    Double eitherMoreThanZeroFull(Double value);

    Integer eitherMoreThanZeroEqualFull(Integer value);

    Double eitherMoreThanZeroEqualFull(Double value);
}
