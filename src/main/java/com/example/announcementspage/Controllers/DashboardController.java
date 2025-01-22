package com.example.announcementspage.Controllers;

import com.example.announcementspage.services.PrivilegesService;
import commons.PrivilegesUtils;
import commons.entities.User;
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
    private final PrivilegesService privilegesService;

    public DashboardController(PrivilegesService privilegesService) {
        this.privilegesService = privilegesService;
    }

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
        User user = new User();

        return privilegesService.checkPrivilege(user, PrivilegesUtils.CREATE_NEW_ANNOUNCEMET) ?
                responseToHtml("/CreateNew") : responseToHtml("/dashboard");
    }

    private ResponseEntity<Void> responseToHtml(String uri) {
        headers = new HttpHeaders();
        headers.setLocation(URI.create(uri));
        return new ResponseEntity<>(headers, HttpStatus.MOVED_PERMANENTLY);
    }
}
