package com.example.spring_boot.repositories;

import com.example.spring_boot.models.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

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
