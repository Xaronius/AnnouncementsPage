package com.example.announcementspage.repository;

import commons.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long>, CustomUserRepository {
    User findByLogin(String login);
}
