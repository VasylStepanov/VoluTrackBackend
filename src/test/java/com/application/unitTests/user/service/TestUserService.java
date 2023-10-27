package com.application.unitTests.user.service;

import com.application.user.model.User;
import com.application.user.model.UserRole;
import com.application.user.service.impl.UserServiceImpl;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Transactional
@Sql("/db/test/user_added.sql")
@SpringBootTest
@TestMethodOrder(MethodOrderer.MethodName.class)
public class TestUserService {

    @Autowired
    private UserServiceImpl userService;

    @Test
    @Order(1)
    public void testFindUserById_ValidId_ReturnUser(){
        User user = User.builder()
                .id(UUID.fromString("71119396-8694-11ed-9ef6-77042ee83937"))
                .fullName("Test name")
                .email("test123@gmail.com")
                .password("$2a$10$KPM4tz/GthS5apgEDljFsOD9IIz2Bg.VG1JHBzMTq.YC3pkq2XeRG")
                .role(UserRole.USER_V_I)
                .build();

        User result = userService.findById(UUID.fromString("71119396-8694-11ed-9ef6-77042ee83937"));

        Assertions.assertNotNull(result);
        Assertions.assertEquals(user, result);
    }

    @Test
    @Order(2)
    public void testFindUserByEmail_InvalidId_ThrowException(){
        Assertions.assertThrows(IllegalStateException.class, () -> userService.findById(UUID.fromString("17a996cb-41c0-4fe1-a68f-7d48656ce95f")));
    }

    @Test
    @Order(3)
    public void testFindUserByEmail_ValidEmail_ReturnUser(){
        User user = User.builder()
                .id(UUID.fromString("9a87155e-7401-11ee-b962-0242ac120002"))
                .fullName("George Washington")
                .email("washington111@gmail.com")
                .password("$2a$12$/Opv4xWTcWwIWCqYdEdgNulQaRODZhhuUnkAqJcjfNeclIynbEYwm")
                .role(UserRole.USER_V_I)
                .build();

        User result = userService.findUserByEmail("washington111@gmail.com");

        Assertions.assertNotNull(result);
        Assertions.assertEquals(user, result);
    }

    @Test
    @Order(4)
    public void testFindUserByEmail_InvalidEmail_ThrowException(){
        Assertions.assertThrows(IllegalStateException.class, () -> userService.findUserByEmail("washington11@gmail.com"));
    }
}
