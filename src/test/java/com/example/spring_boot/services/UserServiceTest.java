package com.example.spring_boot.services;

import com.example.spring_boot.models.User;
import com.example.spring_boot.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class UserServiceTest {

  // TODO użyć mockito (od słowa mock)

  // przykład z mockito
//  @Mock
//  private UserRepository userRepository;
//  @InjectMocks
//  private UserService userService;
//
//  @Test
//  public void tesT() {
//    when(userRepository.findAll())
//      .thenReturn(List.of(new User("John Doe", ""));
//
//    List<User> list = userService.getList();
//
//    assertEquals(1, list.size());
//    assertEquals(list.get(0).name, "John doe");
//  }
}
