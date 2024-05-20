package com.application.content.items.item.service;

public interface ItemValidation {

    String eitherNameValid(String name);

    String eitherDescriptionValid(String description);

    Integer eitherIntegerMoreThanZero(Integer value);

    Integer eitherIntegerMoreThanZeroEqual(Integer value);

    String eitherNameValidFull(String name);

    Integer eitherIntegerMoreThanZeroFull(Integer value);

    Integer eitherIntegerMoreThanZeroEqualFull(Integer value);

}
