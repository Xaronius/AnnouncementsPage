package com.example.announcementspage.Controllers;

import ch.qos.logback.core.util.StringUtil;
import com.example.announcementspage.services.UserService;
import commons.entities.User;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Objects;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;
    HttpHeaders headers;
    private User user;

    @PostMapping("/login")
    public ResponseEntity<Void> handleFormSubmit(@RequestParam String username, @RequestParam String password, HttpSession session, Model model) {

        if (StringUtil.isNullOrEmpty(password))
            return responseToHtml("/LoginPage");
        if (!StringUtil.isNullOrEmpty(username)) {
            model.addAttribute("username", username);
            model.addAttribute("password", password);
            this.user = userService.findUserByUsername(username);
        }
        if (Objects.isNull(user)) {
            return responseToHtml("/LoginPage");
        }
        if (userService.checkPasswordById(password, user)) {
            session.setAttribute("loggedUser", user);
            return responseToHtml("/dashboard");
        }
        return responseToHtml("/LoginPage");
    }

    @PostMapping("/register")
    public ResponseEntity<Void> handleFormSubmit(@RequestParam String username, @RequestParam String email, @RequestParam String password, Model model) {

        headers = new HttpHeaders();
        if (StringUtil.isNullOrEmpty(password) || StringUtil.isNullOrEmpty(username) || StringUtil.isNullOrEmpty(email)) {
            model.addAttribute("username", username);
            model.addAttribute("email", email);
            model.addAttribute("password", password);
            return responseToHtml("/RegisterPage");
        }
        if (Objects.nonNull(userService.findUserByUsername(username))) {
            model.addAttribute("message", "User already exist!");
            //return responseToHtml("/RegisterPage");
        }

        try {
            User newUser = userService.createUser(username, email, password);
            if (Objects.nonNull(newUser)) {
                model.addAttribute("message", "Registration successful!");
            }
            return responseToHtml("/LoginPage");
        } catch (Exception e) {
            model.addAttribute("error", "An error occurred during registration.");
            return responseToHtml("/RegisterPage");
        }
    }

    private ResponseEntity<Void> responseToHtml(String uri) {
        headers.setLocation(URI.create(uri));
        return new ResponseEntity<>(headers, HttpStatus.MOVED_PERMANENTLY);
    }
}
