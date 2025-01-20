package com.example.announcementspage.Controllers;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("/mainpage")
public class DashboardController {
    HttpHeaders headers;

    @GetMapping("/dashboard")
    private ResponseEntity<Void> responseToDashboard() {
        headers = new HttpHeaders();
        headers.setLocation(URI.create("/dashboard"));
        return new ResponseEntity<>(headers, HttpStatus.MOVED_PERMANENTLY);
    }

    @GetMapping("/AllAnnouncements")
    private ResponseEntity<Void> responseToAllAnnouncements() {
        headers.setLocation(URI.create("/AllAnnouncements"));
        return new ResponseEntity<>(headers, HttpStatus.MOVED_PERMANENTLY);
    }

    @GetMapping("/CreateNew")
    private ResponseEntity<Void> responseToCreateNew() {
        headers.setLocation(URI.create("/CreateNew"));
        return new ResponseEntity<>(headers, HttpStatus.MOVED_PERMANENTLY);
    }
}
