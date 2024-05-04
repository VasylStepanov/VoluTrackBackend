package com.application.content.groups.group.service.impl;

import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

@Service
public class GroupValidation {

    @SneakyThrows
    public String eitherNameIsValid(String name){
        if(name.length() < 2 || name.length() > 32)
            throw new RuntimeException("Name is invalid");
        return name;
    }

    @SneakyThrows
    public String eitherDescriptionIsValid(String description) {
        if(description.length() > 128 )
            throw new RuntimeException("Description is invalid");
        return description;
    }


    @SneakyThrows
    public String eitherNameIsValidFull(String name){
        if(name == null)
            throw new RuntimeException("Name is empty");
        return eitherNameIsValid(name);
    }

    @SneakyThrows
    public String eitherDescriptionIsValidFull(String description) {
        if(description == null)
            return null;
        return eitherDescriptionIsValid(description);
    }

}
