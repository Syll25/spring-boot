package com.example.spring_boot.services;

import com.example.spring_boot.services.types.LoginDTO;
import com.example.spring_boot.services.types.UserDTO;
import com.example.spring_boot.models.User;
import com.example.spring_boot.repositories.UserRepository;
import com.example.spring_boot.services.types.UserPageDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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

    public boolean login(LoginDTO loginDTO, HttpServletRequest request) {
        User user = userRepository.findByEmail(loginDTO.login())
                .orElse(null);
        if (user != null && passwordEncoder.matches(loginDTO.password(), user.password)) {
            HttpSession session = request.getSession(true);
            session.setAttribute("user", user);

            return true;

        }
        return false;
    }

    public UserPageDTO getList(Pageable pageable) {
        Page<User> all = userRepository.findAll(pageable);
        List<User> users = all.getContent();
        int currentPage = pageable.getPageNumber();
        int totalPages = (int) Math.ceil((double) all.getTotalElements() / pageable.getPageSize());

        return new UserPageDTO(users, currentPage, totalPages, all.getTotalElements());
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
