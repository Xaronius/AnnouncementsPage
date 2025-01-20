package com.example.announcementspage.repository.impl;

import com.example.announcementspage.repository.CustomUserRepository;
import commons.entities.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.sql.*;

@Repository
public class CustomUserRepositoryImpl implements CustomUserRepository {
    private String url = "jdbc:sqlite:./sqlite/announcementsapp_sqlite.db";
    @PersistenceContext
    private EntityManager em;

    @Override
    public User findUserByUsername(String username) {
        return null;
    }

    @Override
    public String getHashByUserId(Long userId) {
        try (Connection conn = DriverManager.getConnection(url)) {
            String sql = String.format("SELECT PASSWORD_011.HASH_011 FROM PASSWORD_011 LEFT JOIN USERS_010 ON USERS_010.ID_010 = PASSWORD_011.USER_010_011 WHERE PASSWORD_011.USER_010_011 = %s", userId.toString());
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, 1);
                ResultSet rs = pstmt.executeQuery();
                while (rs.next()) {
                    System.out.println("User: " + rs.getString("username"));
                }
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return null;

    }
}
