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
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.validation.BindingResult;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @Mock
    private UserService userService;

    @Mock
    private BindingResult bindingResult;

    @Mock
    private MockHttpServletRequest request;

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
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(user1.getUsername())
                .toUri();

        ResponseEntity<User> response = ResponseEntity
                .created(location)
                .body(user1);
        assertThat(userController.createUser(jsonUser, bindingResult)).isEqualTo(response);
    }

    @Test
    void createUser__user_not_valid() {
        when(bindingResult.hasErrors()).thenReturn(true);
        assertThatThrownBy(() -> userController.createUser(jsonUser, bindingResult));
    }

    @Test
    void getUserById() {
        when(userService.getUserByIdOrThrow(user1.getId())).thenReturn(user1);
        assertThat(userController.getUserById(user1.getId())).isEqualTo(user1);
    }

    @Test
    void deleteUser() {
        userController.deleteUser(user1.getId());
        verify(userService, times(1)).deleteUser(user1.getId());
    }

    @Test
    void updateUser() {
        when(userService.updateUser(user1.getId(), jsonUser)).thenReturn(user1);
        assertThat(userController.updateUser(user1.getId(), jsonUser, bindingResult)).isEqualTo(user1);
    }

    @Test
    void updateUser__user_not_valid() {
        when(bindingResult.hasErrors()).thenReturn(true);
        assertThatThrownBy(() -> userController.updateUser(user1.getId(), jsonUser, bindingResult));
    }
}