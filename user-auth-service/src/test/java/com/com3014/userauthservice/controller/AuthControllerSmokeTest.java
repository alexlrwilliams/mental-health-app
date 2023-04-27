package com.com3014.userauthservice.controller;

import com.com3014.userauthservice.UnitTestHelper;
import com.com3014.userauthservice.model.json.JsonAuth;
import com.com3014.userauthservice.model.json.JsonTokenResponse;
import com.com3014.userauthservice.model.json.user.JsonCreateUser;
import com.com3014.userauthservice.repository.RedisTokenRepository;
import com.com3014.userauthservice.service.JwtService;
import com.com3014.userauthservice.service.UserService;
import org.hamcrest.CoreMatchers;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = AuthController.class)
@WithMockUser("user")
class AuthControllerSmokeTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    private JwtService jwtService;

    @MockBean
    private RedisTokenRepository redisTokenRepository;

    @Test
    public void register__valid() throws Exception {
        when(userService.createUser(any(JsonCreateUser.class))).thenReturn(UnitTestHelper.testUser1);
        mockMvc.perform(
                        post("/api/auth/register")
                                .with(csrf())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("""
                                {
                                	"email": "alex@gmail.com",
                                	"password": "ahh",
                                	"role": "PATIENT",
                                	"firstName": "Alex",
                                	"lastName": "Williams",
                                	"address": "test"
                                }
                                """)
                )
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    public void register__not_valid() throws Exception {
        var expectedMessage = List.of("lastName must not be blank",
                "role must not be null",
                "address must not be blank",
                "email must be a well-formed email address",
                "password must not be blank",
                "firstName must not be blank");
        mockMvc.perform(
                post("/api/auth/register")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                	"email": "ail.com",
                                	"password": "",
                                	"firstName": "",
                                	"lastName": "",
                                	"address": ""
                                }
                                """)
                )
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.message",
                        CoreMatchers.allOf(
                                expectedMessage.stream()
                                        .map(Matchers::containsString)
                                        .toArray(Matcher[]::new))));
    }

    @Test
    public void login__valid() throws Exception {
        when(userService.authenticateCredentials(any(JsonAuth.class)))
                .thenReturn(new JsonTokenResponse("access", "refresh"));
        mockMvc.perform(
                post("/api/auth/login")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                	"email": "alex@gmail.com",
                                	"password": "ahh"
                                }
                          """)
                )
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    public void login__invalid() throws Exception {
        var expectedMessage = List.of(
                "email must be a well-formed email address",
                "password must not be blank");

        mockMvc.perform(
                        post("/api/auth/login")
                                .with(csrf())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("""
                                {
                                	"email": "alexgmail.com",
                                	"password": ""
                                }
                          """)
                )
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.message",
                        CoreMatchers.allOf(
                                expectedMessage.stream()
                                        .map(Matchers::containsString)
                                        .toArray(Matcher[]::new))));
    }



}