package com.application.user.validator;

import com.application.registration.exception.ValidationException;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.stereotype.Service;

@Service
public class UserDataValidator {

    public String eitherFirstNameIsValid(String firstname) throws ValidationException {
        if(firstname.length() < 2 || firstname.length() >= 64)
            throw new ValidationException("Firstname is invalid, firstname should start with capital letter and longer than 2 symbols");
        return firstname;
    }

    public String eitherLastNameIsValid(String lastname) throws ValidationException {
        if(lastname.length() < 2 || lastname.length() >= 64)
            throw new ValidationException("Lastname is invalid, lastname should start with capital letter and longer than 2 symbols");
        return lastname;
    }

    public String eitherEmailIsValid(String email) throws ValidationException {
        if(EmailValidator.getInstance().isValid(email))
            return email;
        throw new ValidationException("Email is invalid!");
    }

    public String eitherPasswordIsValid(String password) throws ValidationException {
        final String PASSWORD_REGEX_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[_!@#&()–[{}]:;',?/*~$^+=<>]).{8,20}$";
        if(password.matches(PASSWORD_REGEX_PATTERN))
            return password;
        throw new ValidationException("Password is invalid! Password should contain at least 1 Upper case letter, 1 symbol and 1 number, and 8 or more letters, but less than 20.");
    }

    public String eitherFirstNameIsValidFull(String firstname) throws ValidationException {
        if(firstname == null)
            throw new ValidationException("Firstname is invalid, firstname should start with capital letter and longer than 2 symbols");
        return eitherFirstNameIsValid(firstname);
    }

    public String eitherLastNameIsValidFull(String lastname) throws ValidationException {
        if(lastname == null)
            throw new ValidationException("Lastname is invalid, lastname should start with capital letter and longer than 2 symbols");
        return eitherLastNameIsValid(lastname);
    }

    public String eitherPasswordIsValidFull(String password) throws ValidationException {
        if(password == null)
            throw new ValidationException("Password is invalid! Password should contain at least 1 Upper case letter, 1 symbol and 1 number, and 8 or more letters, but less than 20.");
        return eitherPasswordIsValid(password);
    }
}
