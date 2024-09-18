package com.example.spring_boot.services;

import com.example.spring_boot.services.types.ListItemUserDTO;
import com.example.spring_boot.services.types.LoginDTO;
import com.example.spring_boot.services.types.UserDTO;
import com.example.spring_boot.models.User;
import com.example.spring_boot.repositories.UserRepository;
import com.example.spring_boot.services.types.UserPageDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Service;
import org.springframework.security.authentication.AuthenticationManager;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserService {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private AuthenticationHandler authenticationHandler;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserRepository userRepository;

    @Transactional
    public UserDTO createUser(UserDTO userDTO) {
        User user = mapToUser(userDTO);
        userRepository.save(user);
        return new UserDTO(user.name, user.email, null, user.age);
    }

    @Transactional
    public boolean login(LoginDTO loginDTO, HttpServletRequest request, HttpServletResponse response) {
        User user = userRepository.findByEmail(loginDTO.login())
                .orElse(null);
        if (user != null && passwordEncoder.matches(loginDTO.password(), user.password)) {
            HttpSession session = request.getSession(true);
            session.setAttribute("user", user);
            UsernamePasswordAuthenticationToken token = UsernamePasswordAuthenticationToken
                    .unauthenticated(loginDTO.login(), loginDTO.password());
            token.setDetails(new WebAuthenticationDetails(request));
            try {
                Authentication authentication = authenticationManager.authenticate(token);
                authenticationHandler.handleSuccessfulAuthentication(user.email, request, response, authentication);
            } catch (AuthenticationException e) {
                authenticationHandler.handleFailedAuthentication(user.email, request, response);
            }

            return true;

        }
        return false;
    }

    public UserPageDTO getList(Pageable pageable) {
        Page<User> all = userRepository.findAll(pageable);
        List<User> users = all.getContent();

        List<ListItemUserDTO> listItemUserDTOS = users.stream()
                .map(user -> new ListItemUserDTO(user.getName(), user.getEmail(), user.getAge()))
                .collect(Collectors.toList());

        int currentPage = pageable.getPageNumber();
        int totalPages = (int) Math.ceil((double) all.getTotalElements() / pageable.getPageSize());

        return new UserPageDTO(listItemUserDTOS, currentPage, totalPages, all.getTotalElements());
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
