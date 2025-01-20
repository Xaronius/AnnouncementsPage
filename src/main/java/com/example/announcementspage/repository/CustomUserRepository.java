package com.example.announcementspage.repository;

import commons.entities.User;

public interface CustomUserRepository {

    User findUserByUsername(String username);
}
