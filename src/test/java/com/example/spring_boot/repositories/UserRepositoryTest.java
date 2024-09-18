package com.example.spring_boot.repositories;

import com.example.spring_boot.models.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Testcontainers
public class UserRepositoryTest {

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
    public void savesEntity() {
        User user = prepareUser("john.doe1@mail.com");
        userRepository.save(user);

        assertNotNull(user.id);
    }

    @Test
    public void findsEntityById() {
        User user = prepareUser("john.doe2@mail.com");
        userRepository.save(user);

        Optional<User> foundUser = userRepository.findById(user.id);

        assertTrue(foundUser.isPresent());
        assertEquals(user.id, foundUser.get().id);
        assertEquals(user.name, foundUser.get().name);
    }

    @Test
    public void findsEntityByEmail() {
        User user = prepareUser("john.deo3@gmail.com" + System.currentTimeMillis());
        userRepository.save(user);

        Optional<User> foundUser = userRepository.findByEmail(user.email);

        assertTrue(foundUser.isPresent());
        assertEquals(user.id, foundUser.get().id);
        assertEquals(user.email, foundUser.get().email);
    }

    private static User prepareUser(String email) {
        User user = new User();
        user.name = "John Doe";
        user.email = email;
        user.password = "password";
        return user;
    }
}