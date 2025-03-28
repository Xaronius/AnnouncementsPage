package com.example.announcementspage.Controllers;

import ch.qos.logback.core.util.StringUtil;
import com.example.announcementspage.services.UserService;
import commons.entities.Password;
import commons.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.net.URI;
import java.util.Map;
import java.util.Objects;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;
    HttpHeaders headers;
    private User user;

//    @PostMapping("/processlogin")
//    public ResponseEntity<Void> handleFormSubmit(@RequestParam String login, @RequestParam String password, Model model) {
//
//        if (StringUtil.isNullOrEmpty(password))
//            return responseToHtml("/LoginPage");
//        if (!StringUtil.isNullOrEmpty(login)) {
//            model.addAttribute("username", login);
//            model.addAttribute("password", password);
//            this.user = userService.findUserByUsername(login);
//        }
//        if (Objects.isNull(user)) {
//            return responseToHtml("/LoginPage");
//        }
//        if (userService.checkPasswordById(password, user)) {
//            return responseToHtml("/dashboard");
//        }
//        return responseToHtml("/LoginPage");
//    }

    @PostMapping("/register")
    public ResponseEntity<Void> handleFormSubmit(@RequestParam String username, @RequestParam String email, @RequestParam String password, Model model) {

        if (StringUtil.isNullOrEmpty(password) || StringUtil.isNullOrEmpty(username) || StringUtil.isNullOrEmpty(email)) {
            model.addAttribute("username", username);
            model.addAttribute("email", email);
            model.addAttribute("password", password);
            return responseToHtml("/RegisterPage");
        }
        if (Objects.nonNull(userService.findUserByUsername(username))) {
            model.addAttribute("message", "User already exist!");
            return responseToHtml("/RegisterPage");
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
        headers = new HttpHeaders();
        headers.setLocation(URI.create(uri));
        return new ResponseEntity<>(headers, HttpStatus.MOVED_PERMANENTLY);
    }

    @GetMapping("/getUserLogin")
    public ResponseEntity<String> getUserLogin(@RequestParam Long userId) {
        User user = userService.findUserById(userId);
        if (user != null)
        {
            return ResponseEntity.ok(user.getLogin());  // Return the user's login
        }
        else
        {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
    }

    @GetMapping("/getUsername")
    public ResponseEntity<String> getUsername(Authentication authentication) {
        String username = authentication.getName(); // Get the logged-in username
        if (username != null) {
            return ResponseEntity.ok(username);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not authenticated");
        }
    }

    @PostMapping("/resetEmail")
    public ResponseEntity<Map<String, String>> resetEmail(@RequestParam String oldEmail, @RequestParam String newEmail, Authentication authentication) {
        // Retrieve the logged-in user's username
        String username = authentication.getName();
        User user = userService.findUserByUsername(username);

        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message", "User not found"));
        }

        // Check if the old email matches the user's current email
        if (!user.getEmail().equals(oldEmail)) {
            System.out.println("BAD EMAIL MATCH! User email in DB: " + user.getEmail());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message", "Old email does not match"));
        }

        // Check if the new email is valid (you can add additional validation here)
        if (newEmail == null || newEmail.isEmpty()) {
            System.out.println("EMPTY FIELDS! User email in DB: " + user.getEmail());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message", "New email is required"));
        }

        // Update the email
        user.setEmail(newEmail);
        userService.save(user); // Save the updated user

        return ResponseEntity.ok(Map.of("message", "Email updated successfully"));
    }
}
