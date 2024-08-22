package com.example.spring_boot.services;

import com.example.spring_boot.services.types.UserDTO;
import com.example.spring_boot.models.User;
import com.example.spring_boot.repositories.UserRepository;
import com.example.spring_boot.services.types.UserPageDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {


    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserRepository userRepository;

    public User createUser(UserDTO userDTO) {
        User user = mapToUser(userDTO);
        return userRepository.save(user);
    }

    public UserPageDTO getList(Pageable pageable) {
        List<User> users = userRepository.findAll(pageable).getContent();
        int currentPage = pageable.getPageNumber();
        int totalPages = (int) Math.ceil((double) userRepository.count() / pageable.getPageSize());
        long totalItems = userRepository.count();

        return new UserPageDTO(users, currentPage, totalPages, totalItems);
    }

    private User mapToUser(UserDTO userDTO) {
        User user = new User();
        user.name = userDTO.name();
        user.email = userDTO.email();
        user.password = passwordEncoder.encode(userDTO.password());
        user.age = userDTO.age();
        return user;
    }
}
