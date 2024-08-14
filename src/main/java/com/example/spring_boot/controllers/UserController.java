package com.example.spring_boot.controllers;

import com.example.spring_boot.models.UserDTO;
import com.example.spring_boot.models.User;
import com.example.spring_boot.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.crypto.password.PasswordEncoder;


@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userservice;
    //private UserRepository userRepository;  zamiast repozytorium, service?
    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping
    public User createUser(@RequestBody UserDTO userDTO) {
       userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));
       return userservice.createUser(userDTO);
    }

}
