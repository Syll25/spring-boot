package com.example.spring_boot.controllers;

import com.example.spring_boot.services.types.UserDTO;
import com.example.spring_boot.models.User;
import com.example.spring_boot.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userservice;
    //private UserRepository userRepository;  zamiast repozytorium, service?

    @PostMapping
    public User createUser(@RequestBody UserDTO userDTO) {
       return userservice.createUser(userDTO);
    }

    // TODO
    //GET /users?page=3&pageSize=5
    //  ale
    // ma to działac tylko dla zalogowanych użytkowników
}
