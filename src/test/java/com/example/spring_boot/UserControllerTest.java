package com.example.spring_boot;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class UserControllerTest {

    @Autowired
    private RestTemplate restTemplate;

//    @Test
//    public void testCreateUser() {
//        String url = "http://localhost:8080/api/users";
//
//        User user = new User();
//        user.name("Jan Kowalski");
//        user.email("jan.kowalski@example.com");
//        user.password("password");
//
//        ResponseEntity<User> response = restTemplate.postForEntity(url, user, User.class);
//
//        assertEquals(200, response.getStatusCodeValue());
//        assertEquals("Jan", response.getBody().getFirstName());
//    }
}
