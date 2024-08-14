package com.example.spring_boot.services;

import com.example.spring_boot.services.types.UserDTO;
import com.example.spring_boot.models.User;
import com.example.spring_boot.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.w3c.dom.stylesheets.LinkStyle;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public User createUser(UserDTO userDTO) {
        User user = mapToUser(userDTO);
        return userRepository.save(user);
    }

    public List<User> getList() {
        // todo
        return null;
    }

    private User mapToUser(UserDTO userDTO) {
        User user = new User();
        user.setName(userDTO.name());
        user.setEmail(userDTO.email());
        user.setPassword(passwordEncoder.encode(userDTO.password()));
        user.setAge(userDTO.age());
        return user;
    }
}
