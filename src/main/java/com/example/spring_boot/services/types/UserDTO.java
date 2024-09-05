package com.example.spring_boot.services.types;

public record UserDTO(
        String name,
        String email,
        String password, // nie chcemy tego wysyłać w odpowiedzi, całe DTO używamy w wielu miejscach, więc stwórzmy dedykowane dla każdego przypadku
        int age
) {

}
