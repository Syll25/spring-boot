package com.example.spring_boot.services;

import com.example.spring_boot.models.User;
import com.example.spring_boot.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

  @Autowired
  private UserRepository userRepository;

  public User createUser(User user) {
    // TODO mapowanie UserDto na User + bcrypt i dopiero zapis
    return userRepository.save(user);
  }
}
