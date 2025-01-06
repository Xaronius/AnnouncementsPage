package com.example.announcementspage.repository;

import commons.entities.User;

public interface UserRepository {
    User findUserByUsername(String username);

}
