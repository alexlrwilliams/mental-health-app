package com.com3014.userauthservice.controller;

import com.com3014.userauthservice.UnitTestHelper;
import com.com3014.userauthservice.exceptions.InvalidTokenException;
import com.com3014.userauthservice.exceptions.UserNotValidException;
import com.com3014.userauthservice.model.BlacklistedToken;
import com.com3014.userauthservice.model.User;
import com.com3014.userauthservice.model.json.JsonAuth;
import com.com3014.userauthservice.model.json.JsonTokenResponse;
import com.com3014.userauthservice.model.json.user.JsonCreateUser;
import com.com3014.userauthservice.model.json.TokenValidationRequest;
import com.com3014.userauthservice.repository.RedisTokenRepository;
import com.com3014.userauthservice.service.JwtService;
import com.com3014.userauthservice.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthControllerTest {

    @Mock
    private UserService userService;

    @Mock
    private JwtService jwtService;

    @Mock
    private RedisTokenRepository redisTokenRepository;

    @Mock
    private BindingResult bindingResult;

    @Mock
    private MockHttpServletRequest request;

    @InjectMocks
    private AuthController authController;

    @Captor
    ArgumentCaptor<BlacklistedToken> tokenIdArgumentCaptor;

    private final User user1 = UnitTestHelper.testUser1;

    private final TokenValidationRequest tokenValidationRequest = new TokenValidationRequest("email", "token");

    private final JsonCreateUser jsonCreateUser = UnitTestHelper.JSON_CREATE_USER;

    private final JsonTokenResponse jsonTokenResponse = new JsonTokenResponse("access", "refresh");


    @Test
    void register() {
        when(userService.createUser(jsonCreateUser)).thenReturn(user1);
        when(jwtService.generateTokenResponse(user1)).thenReturn(jsonTokenResponse);

        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("/api/users/{id}")
                .buildAndExpand(user1.getId())
                .toUri();

        ResponseEntity<JsonTokenResponse> response = ResponseEntity
                .created(location)
                .body(jsonTokenResponse);

        assertThat(authController.register(jsonCreateUser, bindingResult)).isEqualTo(response);
    }

    @Test
    void register__user_not_valid() {
        when(bindingResult.hasErrors()).thenReturn(true);
        assertThatThrownBy(() -> authController.register(jsonCreateUser, bindingResult))
                .isInstanceOf(UserNotValidException.class);
        verify(userService, never()).createUser(any());
        verify(jwtService, never()).generateTokenResponse(any());
    }

    @Test
    void login() {
        var jsonAuth = new JsonAuth("email", "password");
        when(userService.authenticateCredentials(jsonAuth)).thenReturn(jsonTokenResponse);

        ResponseEntity<JsonTokenResponse> expectedResponse = ResponseEntity.ok(jsonTokenResponse);

        assertThat(authController.login(jsonAuth, bindingResult))
                .isEqualTo(expectedResponse);

    }

    @Test
    void login__user_not_valid() {
        var jsonAuth = new JsonAuth("email", "password");
        when(bindingResult.hasErrors()).thenReturn(true);
        assertThatThrownBy(() -> authController.login(jsonAuth, bindingResult))
                .isInstanceOf(UserNotValidException.class);
        verify(userService, never()).authenticateCredentials(any());
    }

    @Test
    void validate() {
        when(userService.loadUserByUsername(tokenValidationRequest.getEmail()))
                .thenReturn(user1);
        when(jwtService.validateAccessToken(tokenValidationRequest.getToken(), user1))
                .thenReturn(true);

        assertThat(authController.validate(tokenValidationRequest, bindingResult))
                .isEqualTo(ResponseEntity.ok(true));
    }

    @Test
    void validate__token_request_not_valid() {
        when(bindingResult.hasErrors()).thenReturn(true);

        assertThatThrownBy(() -> authController.validate(tokenValidationRequest, bindingResult))
                .isInstanceOf(UserNotValidException.class);

        verify(userService, never()).loadUserByUsername(any());
        verify(jwtService, never()).validateAccessToken(any(), any());
    }

    @Test
    void refresh() {
        when(userService.loadUserByUsername(tokenValidationRequest.getEmail())).thenReturn(user1);
        when(jwtService.refreshAccessToken(tokenValidationRequest.getToken(), user1))
                .thenReturn(jsonTokenResponse);

        ResponseEntity<JsonTokenResponse> expectedResponse = ResponseEntity.ok(jsonTokenResponse);

        assertThat(authController.refresh(tokenValidationRequest, bindingResult))
                .isEqualTo(expectedResponse);
    }

    @Test
    void refresh__token_request_not_valid() {
        when(bindingResult.hasErrors()).thenReturn(true);

        assertThatThrownBy(() -> authController.refresh(tokenValidationRequest, bindingResult))
                .isInstanceOf(UserNotValidException.class);

        verify(userService, never()).loadUserByUsername(any());
        verify(jwtService, never()).refreshAccessToken(any(), any());
    }

    @Test
    void logout() {
        when(userService.loadUserByUsername(anyString())).thenReturn(user1);
        when(jwtService.validateAccessToken(tokenValidationRequest.getToken(), user1))
                .thenReturn(true);
        when(jwtService.extractClaim(eq(tokenValidationRequest.getToken()), any()))
                .thenReturn("tokenId");

        authController.logout(tokenValidationRequest, bindingResult);

        verify(redisTokenRepository).save(tokenIdArgumentCaptor.capture());
        assertThat(tokenIdArgumentCaptor.getValue())
                .usingRecursiveComparison()
                .isEqualTo(new BlacklistedToken("tokenId"));
    }

    @Test
    void logout__token_request_not_valid() {
        when(bindingResult.hasErrors()).thenReturn(true);

        assertThatThrownBy(() -> authController.logout(tokenValidationRequest, bindingResult))
                .isInstanceOf(UserNotValidException.class);

        verify(userService, never()).loadUserByUsername(any());
        verify(jwtService, never()).validateAccessToken(any(), any());
        verify(jwtService, never()).extractClaim(any(), any());
        verify(redisTokenRepository, never()).save(any());
    }

    @Test
    void logout__token_invalid() {
        when(userService.loadUserByUsername(anyString())).thenReturn(user1);
        when(jwtService.validateAccessToken(tokenValidationRequest.getToken(), user1))
                .thenReturn(false);

        assertThatThrownBy(() -> authController.logout(tokenValidationRequest, bindingResult))
                .isInstanceOf(InvalidTokenException.class);

        verify(jwtService, never()).extractClaim(any(), any());
        verify(redisTokenRepository, never()).save(any());
    }
}