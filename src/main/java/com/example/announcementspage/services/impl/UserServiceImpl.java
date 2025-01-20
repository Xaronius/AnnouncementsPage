package com.example.announcementspage.services.impl;

import ch.qos.logback.core.util.StringUtil;
import com.example.announcementspage.repository.PasswordRepository;
import com.example.announcementspage.repository.UserRepository;
import com.example.announcementspage.services.UserService;
import commons.entities.Password;
import commons.entities.User;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordRepository passwordRepository;

    public UserServiceImpl(UserRepository userRepository, PasswordRepository passwordRepository) {
        this.userRepository = userRepository;
        this.passwordRepository = passwordRepository;
    }

    @Override
    public User findUserByUsername(String username) {
        if (StringUtil.notNullNorEmpty(username))
            return userRepository.findUserByUsername(username);

        return null;
    }

    @Override
    public Boolean checkPasswordById(String password, Long userId) {
        if (userId > 0) {
            String hash = userRepository.getHashByUserId(userId);

            return BCrypt.checkpw(password, hash);
        }
        return false;
    }

    @Override
    @Transactional
    public User createUser(String username, String email, String password) {

        User user = new User();

        Password password1 = new Password();
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        password1.setHash(passwordEncoder.encode(password));
        passwordRepository.save(password1);

        user.setLogin(username);
        user.setEmail(email);
        user.setPassword(password1);

        return userRepository.save(user);
    }
}
