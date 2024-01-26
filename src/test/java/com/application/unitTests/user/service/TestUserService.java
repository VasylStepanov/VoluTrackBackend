package com.application.unitTests.user.service;

import com.application.user.model.Role;
import com.application.user.model.User;
import com.application.user.repository.RoleRepository;
import com.application.user.service.impl.UserServiceImpl;
import org.junit.jupiter.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger LOG = LoggerFactory.getLogger(TestUserService.class);

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private RoleRepository roleRepository;

    @Test
    @Order(1)
    public void testFindUserById_ValidId_ReturnUser(){
        Role role = roleRepository.findByName("ROLE_USER_V_I").get();

        role.getUsers().stream().forEach(System.out::println);

        User user = User.builder()
                .id(UUID.fromString("71119396-8694-11ed-9ef6-77042ee83937"))
                .fullName("Test name")
                .email("test123@gmail.com")
                .password("$2a$10$KPM4tz/GthS5apgEDljFsOD9IIz2Bg.VG1JHBzMTq.YC3pkq2XeRG")
                .locked(false)
                .enabled(true)
                .role(role)
                .build();

        User result = userService.findById(UUID.fromString("71119396-8694-11ed-9ef6-77042ee83937"));

        Assertions.assertNotNull(result);
        Assertions.assertEquals(user, result);

        LOG.info("Test 1: Successfully accomplished!");
    }

    @Test
    @Order(2)
    public void testFindUserByEmail_InvalidId_ThrowException(){
        Assertions.assertThrows(RuntimeException.class, () -> userService.findById(UUID.fromString("17a996cb-41c0-4fe1-a68f-7d48656ce95f")));
        LOG.info("Test 2: Successfully accomplished!");
    }

    @Test
    @Order(3)
    public void testFindUserByEmail_ValidEmail_ReturnUser(){
        Role role = roleRepository.findByName("ROLE_USER_V_I").get();

        User user = User.builder()
                .id(UUID.fromString("9a87155e-7401-11ee-b962-0242ac120002"))
                .fullName("George Washington")
                .email("washington111@gmail.com")
                .password("$2a$12$/Opv4xWTcWwIWCqYdEdgNulQaRODZhhuUnkAqJcjfNeclIynbEYwm")
                .locked(false)
                .enabled(true)
                .role(role)
                .build();

        User result = userService.findUserByEmail("washington111@gmail.com");

        Assertions.assertNotNull(result);
        Assertions.assertEquals(user, result);

        LOG.info("Test 3: Successfully accomplished!");
    }

    @Test
    @Order(4)
    public void testFindUserByEmail_InvalidEmail_ThrowException(){
        Assertions.assertThrows(RuntimeException.class, () -> userService.findUserByEmail("washington11@gmail.com"));
        LOG.info("Test 4: Successfully accomplished!");
    }
}
