package com.example.spring_boot;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping ("/api")
public class UserController {

    /*
    przygotowanie bazy danych
    - robimy to przy pomocy narzędzia flyway lub liquibase
    - tworzymy plik V1__init.sql
     */


    @PostMapping("/user")
    public void createUser(@RequestBody UserDto userDto) {
        // curl -X POST http://localhost:8080/api/user -d '{"name": "John Kowalsky", "email": "john.kowalski@gmail.com", "password": "p@ssw0rd"}' -H 'Content-Type: application/json'
        User user = new User();
        user.email = userDto.email;
        user.name = userDto.name;
        user.password = userDto.password; // TODO zabezpieczyć - doczytaj
        // TODO przygotować model User
      // TODO uzyc mechanizmu Repository (spring data jpa)
        userRepository.save(user);
      // w curlu musisz zobaczyć 200 OK
    }

    // TODO następny GET /api/users?page=2&size=10

    class UserDto {
        public String name;
        public String email;
        public String password;
    }
}
