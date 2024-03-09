package com.application.volunteers.car.service.impl;

import org.springframework.stereotype.Service;

@Service
public class CarValidation {

    private final String regexNumberEU = "^[A-Z]{1,2}\\d{1,4}[A-Z]{0,3}\\d{0,2}$";
    private final String regexNumberUA = "^[АВЕКМНОРСТУХ]{2}\\d{4}[АВЕКМНОРСТУХ]{2}$";

    public String eitherCarNumberIsValid(String number){
        if(number.matches(regexNumberEU))
            return number;
        if(number.matches(regexNumberUA))
            return number;
        throw new RuntimeException("Car number is invalid");
    }

    public Integer eitherCarCarryingKgIsValid(Integer weight){
        if(weight <= 1 || weight >= 5000)
            throw new RuntimeException("Car weight is invalid");
        return weight;
    }

    public String eitherCarNumberIsValidFull(String number){
        if(number == null)
            throw new RuntimeException("Car number is null");
        return eitherCarNumberIsValid(number);
    }

    public Integer eitherCarCarryingKgIsValidFull(Integer weight){
        if(weight == null)
            throw new RuntimeException("Car weight is null");
        return eitherCarCarryingKgIsValid(weight);
    }

}
