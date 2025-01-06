package com.example.announcementspage.services;

import commons.entities.User;

public interface UserService {
    public User findUserByUsername(String username);
}
