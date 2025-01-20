package com.example.announcementspage.repository.impl;

import com.example.announcementspage.repository.CustomUserRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

@Repository
public class CustomUserRepositoryImpl implements CustomUserRepository {
    private String url = "jdbc:sqlite:./sqlite/announcementsapp_sqlite.db";
    @PersistenceContext
    private EntityManager em;

}
