package com.example.spring_boot.services.types;

import com.example.spring_boot.models.User;

import java.util.List;

public class UserPageDTO {
    public List<User> users;
    public int currentPage;
    public int totalPages;
    public long totalItems;

    public UserPageDTO(List<User> users, int currentPage, int totalPages, long totalItems) {
        this.users = users;
        this.currentPage = currentPage;
        this.totalPages = totalPages;
        this.totalItems = totalItems;
    }
}
