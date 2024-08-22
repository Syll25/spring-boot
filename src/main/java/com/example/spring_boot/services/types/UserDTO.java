package com.example.spring_boot.services.types;

public record UserDTO(
        String name,
        String email,
        String password,
        int age
) {

}
