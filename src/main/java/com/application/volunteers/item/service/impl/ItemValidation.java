package com.application.volunteers.item.service.impl;

import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

@Service
public class ItemValidation {


    @SneakyThrows
    public String eitherNameValid(String name){
        if(name.length() <= 2 || name.length() > 63)
            throw new RuntimeException("Invalid name");
        return name;
    }

    @SneakyThrows
    public String eitherDescriptionValid(String description){
        if(description == null)
            return null;
        if(description.length() <= 2 || description.length() > 511)
            throw new RuntimeException("Invalid description");
        return description;
    }

    @SneakyThrows
    public Integer eitherAmountValid(Integer amount){
        if(amount <= 0 || amount >= 65536)
            throw new RuntimeException("Invalid amount");
        return amount;
    }

    @SneakyThrows
    public String eitherNameValidFull(String name){
        if(name == null)
            throw new RuntimeException("Name is null");
        return eitherNameValid(name);
    }

    @SneakyThrows
    public Integer eitherAmountValidFull(Integer amount){
        if(amount == null)
            throw new RuntimeException("Amount is null");
        return eitherAmountValid(amount);
    }
}
