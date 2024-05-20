package com.application.content.items.item.service.impl;

import com.application.content.items.item.service.ItemValidation;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

@Service
public class ItemValidationImpl implements ItemValidation {


    @SneakyThrows
    @Override
    public String eitherNameValid(String name){
        if(name.length() <= 2 || name.length() > 63)
            throw new RuntimeException("Invalid name");
        return name;
    }

    @SneakyThrows
    @Override
    public String eitherDescriptionValid(String description){
        if(description == null)
            return null;
        if(description.length() <= 2 || description.length() > 511)
            throw new RuntimeException("Invalid description");
        return description;
    }

    @SneakyThrows
    @Override
    public Integer eitherIntegerMoreThanZero(Integer value){
        if(value <= 0 || value >= 65536)
            throw new RuntimeException("Invalid value");
        return value;
    }

    @SneakyThrows
    @Override
    public Integer eitherIntegerMoreThanZeroEqual(Integer value){
        if(value < 0 || value >= 65536)
            throw new RuntimeException("Invalid value");
        return value;
    }

    @SneakyThrows
    @Override
    public String eitherNameValidFull(String name){
        if(name == null)
            throw new RuntimeException("Name is null");
        return eitherNameValid(name);
    }

    @SneakyThrows
    @Override
    public Integer eitherIntegerMoreThanZeroFull(Integer value){
        if(value == null)
            throw new RuntimeException("Value is null");
        return eitherIntegerMoreThanZero(value);
    }

    @SneakyThrows
    @Override
    public Integer eitherIntegerMoreThanZeroEqualFull(Integer value){
        if(value == null)
            throw new RuntimeException("Value is null");
        return eitherIntegerMoreThanZeroEqual(value);
    }
}
