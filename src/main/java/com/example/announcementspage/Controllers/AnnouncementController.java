package com.example.announcementspage.Controllers;

import com.example.announcementspage.repository.AnnouncementRepository;
import commons.entities.Announcement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/announcements")
public class AnnouncementController {

    private final AnnouncementRepository announcementRepository;

    public AnnouncementController(AnnouncementRepository announcementRepository) {
        this.announcementRepository = announcementRepository;
    }

    @GetMapping("/list")
    public ResponseEntity<Map<String, Object>> listAnnouncements(
            @RequestParam(defaultValue = "0") int page,  // Default page is 0 (first page)
            @RequestParam(defaultValue = "10") int size  // Default page size is 10
    ) {
        Pageable pageable = PageRequest.of(page, size); // Create pageable object with the requested page and size
        Page<Announcement> announcementPage = announcementRepository.findAll(pageable);  // Fetch the paginated data

        Map<String, Object> response = new HashMap<>();
        response.put("content", announcementPage.getContent()); // Announcements for the current page
        response.put("totalElements", announcementPage.getTotalElements()); // Total number of announcements
        response.put("totalPages", announcementPage.getTotalPages()); // Total number of pages
        response.put("currentPage", announcementPage.getNumber()); // Current page number

        return ResponseEntity.ok(response);  // Return the data as JSON
    }


    @GetMapping("/first")
    public ResponseEntity<Announcement> getFirstAnnouncement() {
        return announcementRepository.findAll()
                .stream()
                .findFirst()
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.noContent().build());
    }

    @GetMapping("/top5")
    public ResponseEntity<List<Announcement>> getTop5Announcements() {
        List<Announcement> announcements = announcementRepository.findAll()
                .stream()
                .limit(5)
                .toList();
        return ResponseEntity.ok(announcements);
    }
}
