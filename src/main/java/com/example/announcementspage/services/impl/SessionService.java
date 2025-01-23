package com.example.announcementspage.services.impl;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SessionService {
    @Autowired
    private HttpSession session;

    public Long getCurrentUserId() {
        return (Long) session.getAttribute("id");
    }

    public String getLogin() {
        return (String) session.getAttribute("login");
    }

    public String getPasswordHash() {
        return (String) session.getAttribute("password");
    }

    public String getRoleName() {
        return (String) session.getAttribute("role");
    }
}