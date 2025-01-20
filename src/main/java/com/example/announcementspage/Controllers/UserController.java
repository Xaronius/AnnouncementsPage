package com.example.announcementspage.Controllers;

import ch.qos.logback.core.util.StringUtil;
import com.example.announcementspage.services.UserService;
import commons.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController
@RequestMapping("/user")
public class UserController {
    private UserService userService;
    private User user;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserController(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/login")
    public String handleFormSubmit(@RequestParam String username, @RequestParam String password, Model model) {

        if (StringUtil.isNullOrEmpty(password))
            return "LoginPage";
        if (!StringUtil.isNullOrEmpty(username)) {
            model.addAttribute("username", username);
            model.addAttribute("password", password);
            this.user = userService.findUserByUsername(username);
        }
        if (Objects.isNull(user)) {
            return "LoginPage";
        }
        return userService.checkPasswordById(password, user.getId()) ? "index" : "LoginPage";
    }

    @PostMapping("/register")
    public String handleFormSubmit(@RequestParam String username, @RequestParam String email, @RequestParam String password, Model model) {

        if (StringUtil.isNullOrEmpty(password) || StringUtil.isNullOrEmpty(username) || StringUtil.isNullOrEmpty(email)) {
            model.addAttribute("username", username);
            model.addAttribute("email", email);
            model.addAttribute("password", password);
            return "RegisterPage";
        }
        if (Objects.nonNull(userService.findUserByUsername(username))) {
            return "RegisterPage";
        }

        User newUser = userService.createUser(username, email, password);
        return Objects.nonNull(newUser) ? "index" : "RegisterPage";
    }
}
