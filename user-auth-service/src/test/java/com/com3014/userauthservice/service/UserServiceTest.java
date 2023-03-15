package com.com3014.userauthservice.service;

import com.com3014.userauthservice.UnitTestHelper;
import com.com3014.userauthservice.model.User;
import com.com3014.userauthservice.model.json.JsonUser;
import com.com3014.userauthservice.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

import static com.com3014.userauthservice.UnitTestHelper.EMAIL;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    @Captor
    ArgumentCaptor<User> userArgumentCaptor;

    private final User user1 = UnitTestHelper.testUser1;
    private final User user2 = UnitTestHelper.testUser2;

    private final JsonUser jsonUser = UnitTestHelper.jsonUser;

    private final List<User> allUsers = List.of(user1, user2);

    @Test
    void getAllUsers() {
        when(userRepository.findAll()).thenReturn(allUsers);
        assertThat(userService.getAllUsers()).isEqualTo(allUsers);
    }

    @Test
    void getUserByEmail__user_exists() {
        when(userRepository.findUserByEmail(EMAIL)).thenReturn(Optional.of(user1));
        assertThat(userService.getUserByEmail(EMAIL)).isEqualTo(user1);
    }

    @Test
    void getUserByEmail__user_does_not_exist() {
        when(userRepository.findUserByEmail(EMAIL)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> userService.getUserByEmail(EMAIL));
    }

    @Test
    void createUser() {
        when(passwordEncoder.encode(anyString())).thenAnswer(i -> i.getArguments()[0]);
        when(userRepository.save(any(User.class))).thenReturn(user1);
        assertThat(userService.createUser(jsonUser)).isEqualTo(user1);
        verify(userRepository, times(1)).save(userArgumentCaptor.capture());
        assertThat(userArgumentCaptor.getValue())
                .usingRecursiveComparison()
                .isEqualTo(user1);
    }

    @Test
    void deleteUser() {
        when(userRepository.findUserByEmail(EMAIL)).thenReturn(Optional.of(user1));

        userService.deleteUser(EMAIL);

        verify(userRepository, times(1)).delete(userArgumentCaptor.capture());
        assertThat(userArgumentCaptor.getValue())
                .usingRecursiveComparison()
                .isEqualTo(user1);
    }

    @Test
    void updateUser() {
        when(passwordEncoder.encode(anyString())).thenAnswer(i -> i.getArguments()[0]);
        when(userRepository.findUserByEmail(EMAIL)).thenReturn(Optional.of(user1));

        userService.updateUser(EMAIL, jsonUser);

        verify(userRepository, times(1)).save(userArgumentCaptor.capture());
        assertThat(userArgumentCaptor.getValue())
                .usingRecursiveComparison()
                .isEqualTo(user1);
    }
}