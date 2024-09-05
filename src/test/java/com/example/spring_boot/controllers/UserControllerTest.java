package com.example.spring_boot.controllers;

import com.example.spring_boot.models.User;
import com.example.spring_boot.repositories.UserRepository;
import com.example.spring_boot.services.types.LoginDTO;
import com.example.spring_boot.services.types.UserDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class UserControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;
    @Test
    public void testLoginSuccess() throws Exception {
        String encodedPassword = passwordEncoder.encode("password");

        User user = createTestUser("user@example.com", encodedPassword, "User", 25);

        when(userRepository.findByEmail("user@example.com")).thenReturn(Optional.of(user));

        LoginDTO loginDTO = new LoginDTO("user@example.com", "password");

        mvc.perform(MockMvcRequestBuilders.post("/api/user/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginDTO)))
                .andExpect(status().isOk())
                .andExpect(content().string("Login successful"));

    }

    @Test
    public void testLoginFailure() throws Exception {
        LoginDTO loginDTO = new LoginDTO("user@example.com", "wrongpassword");

        mvc.perform(MockMvcRequestBuilders.post("/api/user/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginDTO)))
                .andExpect(status().isUnauthorized())
                .andExpect(content().string("Invalid login"));
    }

    @Test
    public void testCreateUser() throws Exception {
        UserDTO userDTO = new UserDTO("New User", "newuser@example.com", "newpassword", 25);

        mvc.perform(MockMvcRequestBuilders.post("/api/user/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("New User"))
                .andExpect(jsonPath("$.password").doesNotExist());
    }

    @Test
    public void testGetUserList() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/api/user/getList")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.users[*].password").doesNotExist());
    }

    private String getUserName(User user) {
        return user.name;
    }
    private User createTestUser(String email, String password, String name, int age) {
        return new User("user@example.com", null, "user", 25);
    }
}

