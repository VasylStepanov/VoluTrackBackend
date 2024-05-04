package com.application.content.volunteers.car.service.impl;

import org.springframework.stereotype.Service;

@Service
public class CarValidation {

    private final String regexNumberEU = "^[A-Z]{1,2}\\d{1,4}[A-Z]{0,3}\\d{0,2}$";
    private final String regexNumberUA = "^[АВЕКМНОРСТУХ]{2}\\d{4}[АВЕКМНОРСТУХ]{2}$";

    public String eitherNumberIsValid(String number){
        if(number.matches(regexNumberEU))
            return number;
        if(number.matches(regexNumberUA))
            return number;
        throw new RuntimeException("Car number is invalid");
    }

    public String eitherDescriptionIsValid(String description) {
        if(description.length() > 64)
            throw new RuntimeException("Description is too long");
        return description;
    }

    public Integer eitherCarryingKgIsValid(Integer weight){
        if(weight <= 1 || weight >= 5000)
            throw new RuntimeException("Car weight is invalid");
        return weight;
    }

    public String eitherNumberIsValidFull(String number){
        if(number == null)
            throw new RuntimeException("Car number is null");
        return eitherNumberIsValid(number);
    }

    public String eitherDescriptionIsValidFull(String description) {
        return description == null ? null : eitherDescriptionIsValid(description);
    }

    public Integer eitherCarryingKgIsValidFull(Integer weight){
        if(weight == null)
            throw new RuntimeException("Car weight is null");
        return eitherCarryingKgIsValid(weight);
    }

}
