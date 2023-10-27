package com.application.user.validator;

import com.application.registration.exception.ValidationException;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.stereotype.Service;

@Service
public class UserDataValidator {

    public String eitherEmailIsValid(String email) throws ValidationException {
        if(EmailValidator.getInstance().isValid(email))
            return email;
        throw new ValidationException("Email is invalid!");
    }

    public String eitherPasswordIsValid(String password) throws ValidationException {
        final String PASSWORD_REGEX_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[_!@#&()–[{}]:;',?/*~$^+=<>]).{8,20}$";
        if(password.matches(PASSWORD_REGEX_PATTERN))
            return password;
        throw new ValidationException("Password is invalid!");
    }
}
