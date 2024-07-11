package com.example.spring_boot;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("/")
    public String index() {
        return "Greetings from Spring Boot!";  //get
    }

    @PostMapping("/hello")
    public String postHello() {
        return "Post: Greeting from Spring Boot"; //post
    }

    @PutMapping("/")
    public String putHello() {
         return "Pust: Greeting from Spring Boot";
    }

    @DeleteMapping("/")
    public String deleteHello() {
        return "Delete: Greetings from Spring Boot";
    }

}