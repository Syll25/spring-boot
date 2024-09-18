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
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.web.reactive.server.EntityExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


@AutoConfigureMockMvc
@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@SqlGroup(
        {@Sql(scripts = "classpath:db/fixtures_init.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
                @Sql(scripts = "classpath:db/fixtures_clean.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)}
)
public class UserControllerTest {

    @Autowired
    private MockMvc mvc;

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

    @Container
    public static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:15-alpine")
            .withDatabaseName("testdb")
            .withUsername("test")
            .withPassword("test");

    @DynamicPropertySource
    static void registerPgProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgreSQLContainer::getJdbcUrl);
        registry.add("spring.datasource.username", postgreSQLContainer::getUsername);
        registry.add("spring.datasource.password", postgreSQLContainer::getPassword);
    }
    @Test
    public void testLoginSuccess() throws Exception {
        String encodedPassword = passwordEncoder.encode("password");

        User user = createTestUser("user@example.com", encodedPassword, "User", 25);

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

