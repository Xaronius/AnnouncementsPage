package com.example.announcementspage.services;

import commons.entities.User;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    User findUserByUsername(String username);
    Boolean checkPasswordById(String password, User user);

    User createUser(String username, String email, String password);
}
