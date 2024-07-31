package com.example.spring_boot;

import org.apache.catalina.User;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping ("/api/hello")
public class HelloController {

    @GetMapping("/index")
    public String index() {
        return "GET: Greetings from Spring Boot!";
    }

    @PostMapping("/hello")
    public String postHello() {
        return "Post: Greeting from Spring Boot";
    }

    @PutMapping("/put")
    public String putHello() {
         return "Put: Greeting from Spring Boot";
    }

    @DeleteMapping("/del")
    public String deleteHello() {
        return "Delete: Greetings from Spring Boot";
    }

    @PostMapping("/user")
    public void createUser() {
    }

    @PostMapping("userController")
    public void createUser(@RequestBody User user) {

    }
}