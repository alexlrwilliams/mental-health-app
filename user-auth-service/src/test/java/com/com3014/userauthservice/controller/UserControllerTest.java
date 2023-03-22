package com.com3014.userauthservice.controller;

import com.com3014.userauthservice.UnitTestHelper;
import com.com3014.userauthservice.model.User;
import com.com3014.userauthservice.model.json.JsonUser;
import com.com3014.userauthservice.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static com.com3014.userauthservice.UnitTestHelper.EMAIL;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;
    private final User user1 = UnitTestHelper.testUser1;
    private final User user2 = UnitTestHelper.testUser2;

    private final JsonUser jsonUser = UnitTestHelper.jsonUser;

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
        when(userService.getUserOrThrow(EMAIL)).thenReturn(user1);
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