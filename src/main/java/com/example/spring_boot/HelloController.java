package com.example.spring_boot;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("/index")
    public String index() {
        return "Greetings from Spring Boot!";  //get
    }

    @PostMapping("/hello")
    public String postHello() {
        return "Post: Greeting from Spring Boot"; //post
    }

    @PutMapping("/put")
    public String putHello() {
         return "Put: Greeting from Spring Boot";
    }

    @DeleteMapping("/del")
    public String deleteHello() {
        return "Delete: Greetings from Spring Boot";
    }

}