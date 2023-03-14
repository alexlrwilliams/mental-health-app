package com.com3014.userauthservice.controller;

import com.com3014.userauthservice.model.Authority;
import com.com3014.userauthservice.model.User;
import com.com3014.userauthservice.model.json.JsonUser;
import com.com3014.userauthservice.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    private static final String EMAIL = "john.doe@gmail.com";
    private static final String EMAIL_2 = "jane.smith@gmail.com";
    private static final String PASSWORD = "password";
    private static final List<Authority> AUTHORITIES = List.of(Authority.BOOK_APPOINTMENT, Authority.ACCESS_ALL_PATIENTS);
    private static final String FIRST_NAME = "John";
    private static final String FIRST_NAME_2 = "Jane";
    private static final String LAST_NAME = "Doe";
    private static final String LAST_NAME_2 = "Smith";
    private static final String ADDRESS = "42 Dancing Ave";

    private final User user1 = new User(EMAIL, PASSWORD, AUTHORITIES, FIRST_NAME, LAST_NAME, ADDRESS);
    private final User user2 = new User(EMAIL_2, PASSWORD, AUTHORITIES, FIRST_NAME_2, LAST_NAME_2, ADDRESS);

    private final JsonUser jsonUser = new JsonUser(EMAIL, PASSWORD, AUTHORITIES, FIRST_NAME, LAST_NAME, ADDRESS);

    private final List<User> allUsers = List.of(user1, user2);

    @Test
    void getAllUsers() {
        when(userService.getAllUsers()).thenReturn(allUsers);
        assertThat(userController.getAllUsers()).isEqualTo(allUsers);
    }

    @Test
    void createUser() {
        when(userService.createUser(jsonUser)).thenReturn(user1);
        assertThat(userController.createUser(jsonUser)).isEqualTo(user1);
    }

    @Test
    void getUserByEmail() {
        when(userService.getUserByEmail(EMAIL)).thenReturn(user1);
        assertThat(userController.getUserByEmail(EMAIL)).isEqualTo(user1);
    }

    @Test
    void deleteUser() {
        userController.deleteUser(EMAIL);
        verify(userService, times(1)).deleteUser(EMAIL);
    }

    @Test
    void updateUser() {
        when(userService.updateUser(EMAIL, jsonUser)).thenReturn(user1);
        assertThat(userController.updateUser(EMAIL, jsonUser)).isEqualTo(user1);
    }
}