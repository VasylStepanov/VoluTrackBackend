package com.application.content.volunteers.volunteer.service.impl;

import com.application.content.volunteers.volunteer.service.VolunteerDataValidation;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

@Service
public class VolunteerDataValidationImpl implements VolunteerDataValidation {

    private final static String PHONE_NUMBER_REGEX = "^\\+380\\d{9}$";

    @Override
    @SneakyThrows
    public String eitherPhoneNumberIsValid(String phoneNumber){
        if(phoneNumber.matches(PHONE_NUMBER_REGEX))
            return phoneNumber;
        throw new RuntimeException("Phone is invalid");
    }

    @Override
    @SneakyThrows
    public String eitherPhoneNumberIsValidFull(String phoneNumber){
        if(phoneNumber == null)
            throw new RuntimeException("Phone number is null");
        return eitherPhoneNumberIsValid(phoneNumber);
    }
}
