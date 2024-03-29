package com.com3014.userauthservice.controller;

import com.com3014.userauthservice.UnitTestHelper;
import com.com3014.userauthservice.model.Role;
import com.com3014.userauthservice.model.json.user.JsonUpdateUser;
import com.com3014.userauthservice.model.json.user.JsonCreateUser;
import com.com3014.userauthservice.service.UserService;
import org.hamcrest.CoreMatchers;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = UserController.class)
@WithMockUser("user")
class UserControllerSmokeTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @BeforeEach
    public void setup() {
        MockMultipartFile image = new MockMultipartFile("file", "", "application/json", "{\"image\": \"C:\\Users\\Public\\Pictures\\Sample Pictures\\Penguins.jpg\"}".getBytes());
    }

    @Test
    public void getUser__valid() throws Exception {
        mockMvc.perform(
                get("/api/users")
                        .header("X-Role-Header", Role.DOCTOR)
                        .header("email", UnitTestHelper.EMAIL)
                )
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    public void getUser__invalid() throws Exception {
        mockMvc.perform(
                        get("/api/users")
                                .header("X-Role-Header", Role.PATIENT)
                                .header("email", UnitTestHelper.EMAIL)
                )
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void createUser__valid() throws Exception {
        when(userService.createUser(any(JsonCreateUser.class))).thenReturn(UnitTestHelper.testUser1);
        mockMvc.perform(
                        post("/api/users")
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
    public void createUser__not_valid() throws Exception {
        var expectedMessage = List.of("lastName must not be blank",
                "role must not be null",
                "address must not be blank",
                "email must be a well-formed email address",
                "password must not be blank",
                "firstName must not be blank");
        mockMvc.perform(
                post("/api/users")
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
                .andDo(print())
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.message",
                        CoreMatchers.allOf(
                                expectedMessage.stream()
                                        .map(Matchers::containsString)
                                        .toArray(Matcher[]::new))));
    }
}