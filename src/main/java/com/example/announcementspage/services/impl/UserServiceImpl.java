package com.example.announcementspage.services.impl;

import ch.qos.logback.core.util.StringUtil;
import com.example.announcementspage.repository.UserRepository;
import com.example.announcementspage.services.UserService;
import commons.entities.User;

public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User findUserByUsername(String username) {
        if (StringUtil.notNullNorEmpty(username))
            return userRepository.findUserByUsername(username);

        return null;
    }
}
