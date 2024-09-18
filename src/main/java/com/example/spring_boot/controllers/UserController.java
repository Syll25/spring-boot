package com.example.spring_boot.controllers;

import com.example.spring_boot.services.types.ListItemUserDTO;
import com.example.spring_boot.services.types.LoginDTO;
import com.example.spring_boot.services.types.UserDTO;
import com.example.spring_boot.services.UserService;
import com.example.spring_boot.services.types.UserPageDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<Void> createUser(@RequestBody UserDTO userDTO) {
        try {
            userService.createUser(userDTO);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginDTO loginDTO, HttpServletRequest request, HttpServletResponse response) {
        boolean loggedIn = userService.login(loginDTO, request, response);
        if (loggedIn) {
            return ResponseEntity.ok("Login successful");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid login");
        }

    }

    @GetMapping("/getList")
    public UserPageDTO getUserList(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int pageSize,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String direction) {

        Sort sort = direction.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, pageSize, sort);

        UserPageDTO userPage = userService.getList(pageable);

        List<ListItemUserDTO> safeUsers = userPage.users.stream()
                .map(user -> new ListItemUserDTO(user.name(), user.email(), user.age()))
                .collect(Collectors.toList());

        return new UserPageDTO(safeUsers, userPage.currentPage, userPage.totalPages, userPage.totalItems);
    }

}