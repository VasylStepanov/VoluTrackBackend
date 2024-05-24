package com.application.content.volunteers.volunteer.service;

public interface VolunteerDataValidation {
    String eitherPhoneNumberIsValid(String phoneNumber);

    String eitherPhoneNumberIsValidFull(String phoneNumber);
}
