package com.example.spring_boot.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class SecurityConfig { // raczej powinna byc klasa statyczna (co to? :P) ale lepiej wynieść do osobnego pliku

  @Bean
  public BCryptPasswordEncoder passwordEncoder() { // brak w pom
    return new BCryptPasswordEncoder();
  }
}
