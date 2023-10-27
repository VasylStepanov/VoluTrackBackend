package com.application.unitTests.user;

import com.application.registration.exception.ValidationException;
import com.application.user.validator.UserDataValidator;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


@SpringBootTest
@TestMethodOrder(MethodOrderer.MethodName.class)
public class TestUserDataValidator {


    @Autowired
    private UserDataValidator userDataValidator;

    @Test
    @Order(1)
    public void testEitherEmailIsValid_ValidEmail_ReturnsEmail() throws ValidationException {
        String email = "test@example.com";

        String result = userDataValidator.eitherEmailIsValid(email);

        assertEquals(email, result);
    }

    @Test
    @Order(2)
    public void testEitherEmailIsValid_InvalidEmail_ThrowsValidationException() {
        String email = "testexample.com";

        assertThrows(ValidationException.class, () -> {
            userDataValidator.eitherEmailIsValid(email);
        });
    }

    @Test
    @Order(3)
    public void testEitherPasswordIsValid_ValidPassword_ReturnsPassword() throws ValidationException {
        String password = "passWo_rd123";

        String result = userDataValidator.eitherPasswordIsValid(password);

        assertEquals(password, result);
    }

    @Test
    @Order(4)
    public void testEitherPasswordIsValid_InvalidPassword_ThrowsValidationException() {
        String password = "asdgnd2239";

        assertThrows(ValidationException.class, () -> {
            userDataValidator.eitherPasswordIsValid(password);
        });
    }
}
