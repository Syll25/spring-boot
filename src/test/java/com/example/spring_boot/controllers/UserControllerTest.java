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
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.web.reactive.server.EntityExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@SqlGroup(
  {@Sql(scripts = "classpath:db/fixtures_init.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
    @Sql(scripts = "classpath:db/fixtures_clean.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)}
)
public class UserControllerTest {

    @Autowired
    private MockMvc mvc; // przejdźmy na webTestClient

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    private WebTestClient webTestClient = WebTestClient
      .bindToServer()
      .baseUrl("http://localhost:8080")
      .build();

    @Autowired
    private UserRepository userRepository;
    @Test
    public void testLoginSuccess() throws Exception {
        String encodedPassword = passwordEncoder.encode("password");

        User user = createTestUser("user@example.com", encodedPassword, "User", 25); // niewykorzystane, tylko zwraca obiekt

        //when(userRepository.findByEmail("user@example.com")).thenReturn(Optional.of(user)); // staramy się nie mockować w testach kontrolerów - niech to będą testy jak najbardziej zbliżone do e2e
        // jeśli już chcemy mockować, to nie z użyciem mockito a z użyciem @MockBean (https://www.baeldung.com/java-spring-mockito-mock-mockbean)
        // zamiast tego przygotujmy sobie odpowiednie wpisy w bazie z użyciem @Sql i skryptu fixtures

        LoginDTO loginDTO = new LoginDTO("test@user.com", "somepassword");

        webTestClient.post().uri("/api/user/login")
          .contentType(MediaType.APPLICATION_JSON)
          .bodyValue(loginDTO)
          .exchange()
          .expectStatus().isOk()
          .expectHeader().exists("Set-Cookie")
          .expectBody(String.class).isEqualTo("Login successful");
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
        // co mamy w odpowiedzi? 403 - Forbidden, bo nie jesteśmy zalogowani
        /*mvc.perform(MockMvcRequestBuilders.get("/api/user/getList")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.users[*].password").doesNotExist());*/

        LoginDTO loginDTO = new LoginDTO("test@user.com", "somepassword");
        EntityExchangeResult<String> result = webTestClient.post().uri("/api/user/login")
          .contentType(MediaType.APPLICATION_JSON)
          .bodyValue(loginDTO)
          .exchange()
          .expectStatus().isOk()
          .expectHeader().exists("Set-Cookie")
          .expectBody(String.class).isEqualTo("Login successful")
          .returnResult();

        webTestClient.get().uri("/api/user/getList")
          .cookie("JSESSIONID", result.getResponseCookies().getFirst("JSESSIONID").getValue())
          .exchange()
          .expectStatus().isOk()
          .expectBody()
          .jsonPath("$.users[*].password").doesNotExist();
    }

    @Test
    public void testGetUserListRespondsWith403WhenNotSignedIn() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/api/user/getList")
            .contentType(MediaType.APPLICATION_JSON))
          .andExpect(status().isForbidden());
    }

    private String getUserName(User user) {
        return user.name;
    }
    private User createTestUser(String email, String password, String name, int age) {
        return new User("user@example.com", null, "user", 25);
    }
}

