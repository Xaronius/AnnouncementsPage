package com.example.announcementspage.services;

import commons.entities.User;
import org.springframework.stereotype.Service;

@Service
public interface PrivilegesService {
    Boolean checkPrivilege(User user, String access);
}
