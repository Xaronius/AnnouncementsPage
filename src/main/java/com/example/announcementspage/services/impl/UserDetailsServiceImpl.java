package com.example.announcementspage.services.impl;

import com.example.announcementspage.config.CustomUserDetails;
import com.example.announcementspage.repository.PasswordRepository;
import com.example.announcementspage.repository.UserRepository;
import commons.entities.Password;
import commons.entities.User;
import jakarta.transaction.Transactional;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private PasswordRepository passwordRepository;
    @Autowired
    private UserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepository.findByLogin(username);
        if (user == null) {
            throw new UsernameNotFoundException(String.format("User: %s not found", username));
        }
        Hibernate.initialize(user.getPassword());
        Password password = passwordRepository.getReferenceById(user.getPassword().getId());

        return new CustomUserDetails(user, password.getHash());
    }
}
