package com.example.spring_boot.services.types;

import java.util.List;

public class UserPageDTO {
    public List<ListItemUserDTO> users;
    public int currentPage;
    public int totalPages;
    public long totalItems;

    public UserPageDTO(List<ListItemUserDTO> users, int currentPage, int totalPages, long totalItems) {
        this.users = users;
        this.currentPage = currentPage;
        this.totalPages = totalPages;
        this.totalItems = totalItems;
    }
}
