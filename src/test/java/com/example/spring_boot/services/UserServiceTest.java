package com.example.spring_boot.services;

import com.example.spring_boot.models.User;
import com.example.spring_boot.repositories.UserRepository;
import com.example.spring_boot.services.types.UserPageDTO;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class UserServiceTest {

    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private UserService userService;

    @Test
    public void testGetUserPage() {

        User user = new User();
        user.name = "John Doe";
        user.email = "john.doe@example.com";

        Pageable pageable = PageRequest.of(0, 10);
        Page<User> userPage = new PageImpl<>(List.of(user), pageable, 1);

        when(userRepository.findAll(pageable)).thenReturn(userPage);

        UserPageDTO result = userService.getList(pageable);

        assertEquals(1, result.users.size());
        assertEquals("John Doe", result.users.get(0).name);
        assertEquals(0, result.currentPage);
        assertEquals(1, result.totalPages);
        assertEquals(1, result.totalItems);
    }
}
