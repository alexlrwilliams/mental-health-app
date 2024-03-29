package com.com3014.userauthservice.service;

import com.com3014.userauthservice.UnitTestHelper;
import com.com3014.userauthservice.exceptions.UnauthorisedAccessException;
import com.com3014.userauthservice.exceptions.UserAlreadyExistAuthenticationException;
import com.com3014.userauthservice.model.User;
import com.com3014.userauthservice.model.json.user.JsonCreateUser;
import com.com3014.userauthservice.model.json.user.JsonUpdateUser;
import com.com3014.userauthservice.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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

    @Mock
    private S3Service s3Service;

    @InjectMocks
    private UserService userService;

    @Captor
    ArgumentCaptor<User> userArgumentCaptor;

    private final User user1 = UnitTestHelper.testUser1;
    private final User user2 = UnitTestHelper.testUser2;

    private final JsonCreateUser jsonCreateUser = UnitTestHelper.JSON_CREATE_USER;
    private final JsonUpdateUser jsonUpdateUser = UnitTestHelper.JSON_UPDATE_USER;

    private final List<User> allUsers = List.of(user1, user2);

    @Test
    void getAllUsers() {
        when(userRepository.findAll()).thenReturn(allUsers);
        assertThat(userService.getAllUsers()).isEqualTo(allUsers);
    }

    @Test
    void getUserByEmail__user_exists() {
        when(userRepository.findUserByUsername(EMAIL)).thenReturn(Optional.of(user1));
        assertThat(userService.getUserByEmail(EMAIL)).isEqualTo(Optional.of(user1));
    }

    @Test
    void getUserByEmail__user_does_not_exist() {
        when(userRepository.findUserByUsername(EMAIL)).thenReturn(Optional.empty());
        assertThat(userService.getUserByEmail(EMAIL)).isEqualTo(Optional.empty());
    }

    @Test
    void getUserOrThrow__user_exists() {
        when(userRepository.findUserByUsername(EMAIL)).thenReturn(Optional.of(user1));
        assertThat(userService.getUserByEmailOrThrow(EMAIL)).isEqualTo(user1);
    }

    @Test
    void getUserOrThrow__user_does_not_exist() {
        when(userRepository.findUserByUsername(EMAIL)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> userService.getUserByEmailOrThrow(EMAIL))
                .isInstanceOf(UsernameNotFoundException.class);
    }

    @Test
    void getUserById__user_exists() {
        when(userRepository.findById(user1.getId())).thenReturn(Optional.of(user1));
        assertThat(userService.getUserById(user1.getId())).isEqualTo(Optional.of(user1));
    }

    @Test
    void getUserById__user_does_not_exist() {
        when(userRepository.findById(user1.getId())).thenReturn(Optional.empty());
        assertThat(userService.getUserById(user1.getId())).isEqualTo(Optional.empty());
    }

    @Test
    void getUserByIdOrThrow__user_exists() {
        when(userRepository.findById(user1.getId())).thenReturn(Optional.of(user1));
        assertThat(userService.getUserByIdOrThrow(user1.getId())).isEqualTo(user1);
    }

    @Test
    void getUserByIdOrThrow__user_does_not_exist() {
        when(userRepository.findById(user1.getId())).thenReturn(Optional.empty());
        assertThatThrownBy(() -> userService.getUserByIdOrThrow(user1.getId()))
                .isInstanceOf(UsernameNotFoundException.class);
    }

    @Test
    void createUser__user_does_not_exist() {
        when(passwordEncoder.encode(anyString())).thenAnswer(i -> i.getArguments()[0]);
        when(userRepository.save(any(User.class))).thenReturn(user1);
        assertThat(userService.createUser(jsonCreateUser)).isEqualTo(user1);
        verify(passwordEncoder, times(1)).encode(jsonCreateUser.getPassword());
        verify(userRepository, times(1)).save(userArgumentCaptor.capture());
        assertThat(userArgumentCaptor.getValue())
                .usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(user1);
    }

    @Test
    void createUser__user_exists() {
        when(userRepository.findUserByUsername(user1.getUsername())).thenReturn(Optional.of(user1));
        verify(userRepository, never()).save(any());
        assertThatThrownBy(() -> userService.createUser(jsonCreateUser))
                .isInstanceOf(UserAlreadyExistAuthenticationException.class);
    }

    @Test
    void deleteUser() {
        when(userRepository.findById(user1.getId())).thenReturn(Optional.of(user1));

        userService.deleteUser(user1.getId());

        verify(userRepository, times(1)).delete(userArgumentCaptor.capture());
        assertThat(userArgumentCaptor.getValue())
                .usingRecursiveComparison()
                .isEqualTo(user1);
    }

    @Test
    void updateUser__conflicting_user_does_not_exist() {
        when(userRepository.findById(user1.getId())).thenReturn(Optional.of(user1));
        when(userRepository.findUserByUsername(EMAIL))
                .thenReturn(Optional.of(user1));

        userService.updateUser(user1.getId(), jsonUpdateUser, user1.getUsername(), null);

        verify(userRepository, times(1)).save(userArgumentCaptor.capture());
        assertThat(userArgumentCaptor.getValue())
                .usingRecursiveComparison()
                .isEqualTo(user1);
    }

    @Test
    void updateUser__conflicting_user_exists() {
        when(userRepository.findById(user1.getId())).thenReturn(Optional.of(user1));
        when(userRepository.findUserByUsername(EMAIL))
                .thenReturn(Optional.of(user2));

        verify(userRepository, never()).save(any());
        assertThatThrownBy(() -> userService.updateUser(user1.getId(), jsonUpdateUser, user1.getUsername(), null))
                .isInstanceOf(UserAlreadyExistAuthenticationException.class);
    }

    @Test
    void updateUser__user_unauthorized() {
        when(userRepository.findById(user1.getId())).thenReturn(Optional.of(user1));
        when(userRepository.findUserByUsername(EMAIL))
                .thenReturn(Optional.of(user1));

        verify(userRepository, never()).save(any());
        assertThatThrownBy(() -> userService.updateUser(user1.getId(), jsonUpdateUser, "email", null))
                .isInstanceOf(UnauthorisedAccessException.class);
    }


}