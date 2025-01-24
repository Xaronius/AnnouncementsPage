package com.example.announcementspage.services.impl;

import ch.qos.logback.core.util.StringUtil;
import com.example.announcementspage.repository.PasswordRepository;
import com.example.announcementspage.repository.UserRepository;
import com.example.announcementspage.services.UserService;
import commons.entities.Password;
import commons.entities.User;
import jakarta.transaction.Transactional;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;

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
            return userRepository.findByLogin(username);
        return null;
    }

    @Override
    public Boolean checkPasswordById(String password, User user) {
        if (Objects.nonNull(user)) {
            Password hash = passwordRepository.getReferenceById(user.getPassword().getId());
            return BCrypt.checkpw(password, hash.getHash());
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
        userRepository.save(user);

        return user;
    }

    public User findUserById(Long userId) {
        return userRepository.findById(userId).orElse(null);  // Return user or null if not found
    }

    public boolean isUserAdmin(Long userId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User currentUser = findUserByUsername(username);

        if (currentUser != null) {
            Boolean isAdmin = currentUser.getIsAdmin();
            return isAdmin != null && isAdmin; // If isAdmin is null, it will return false
        }

        return false; // Default return false if the user is not found
    }
}
