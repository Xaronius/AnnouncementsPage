package com.example.announcementspage.Controllers;

import com.example.announcementspage.repository.AnnouncementImageRepository;
import com.example.announcementspage.repository.AnnouncementRepository;
import com.example.announcementspage.repository.CategoryRepository;
import com.example.announcementspage.services.UserService;
import commons.entities.Announcement;
import commons.entities.AnnouncementImage;
import commons.entities.Category;
import commons.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/announcements")
public class AnnouncementController {

    private final AnnouncementRepository announcementRepository;
    private final AnnouncementImageRepository announcementImageRepository;
    private final CategoryRepository categoryRepository;
    //private final UserService userService;

    @Autowired
    private UserService userService;

    public AnnouncementController(AnnouncementRepository announcementRepository, AnnouncementImageRepository announcementImageRepository, CategoryRepository categoryRepository, UserService userService) {
        this.announcementRepository = announcementRepository;
        this.announcementImageRepository = announcementImageRepository;
        this.categoryRepository = categoryRepository;
        //this.userService = userService;
    }

    @GetMapping("/{adId}")
    public ResponseEntity<?> getAnnouncementById(@PathVariable Long adId) {
        Optional<Announcement> announcementOptional = announcementRepository.findById(adId);

        if (announcementOptional.isPresent()) {
            Announcement announcement = announcementOptional.get();

            Map<String, Object> annMap = new HashMap<>();
            annMap.put("id", announcement.getAdId());
            annMap.put("title", announcement.getTitle());
            annMap.put("description", announcement.getDescription());
            annMap.put("contactEmail", announcement.getContactEmail());
            annMap.put("contactPhone", announcement.getContactPhone());
            annMap.put("userId", announcement.getUserId());

            List<String> base64Images = announcement.getImages().stream()
                    .map(AnnouncementImage::getImage)
                    .toList();

            annMap.put("images", base64Images);

            return ResponseEntity.ok(annMap);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Announcement not found");
        }
    }

    @GetMapping("/list")
    public ResponseEntity<Map<String, Object>> listAnnouncements(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) Long categoryId  // Optional category filter
    ) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Announcement> announcementPage;

        // Filter by category if provided
        if (categoryId != null) {
            announcementPage = announcementRepository.findByCategory(categoryId, pageable);
        } else {
            announcementPage = announcementRepository.findAll(pageable);
        }

        List<Map<String, Object>> announcementsWithImages = announcementPage.getContent().stream().map(announcement -> {
            Map<String, Object> annMap = new HashMap<>();
            annMap.put("id", announcement.getAdId());
            annMap.put("title", announcement.getTitle());
            annMap.put("description", announcement.getDescription());
            annMap.put("contactEmail", announcement.getContactEmail());
            annMap.put("contactPhone", announcement.getContactPhone());
            annMap.put("nickName", announcement.getUserId());

            List<String> base64Images = announcement.getImages().stream()
                    .map(AnnouncementImage::getImage)
                    .toList();

            annMap.put("images", base64Images);
            return annMap;
        }).toList();

        Map<String, Object> response = new HashMap<>();
        response.put("content", announcementsWithImages);
        response.put("totalElements", announcementPage.getTotalElements());
        response.put("totalPages", announcementPage.getTotalPages());
        response.put("currentPage", announcementPage.getNumber());

        return ResponseEntity.ok(response);
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
        // Sort the announcements by 'announcementId' in descending order (most recent first)
        List<Announcement> announcements = announcementRepository.findAll(Sort.by(Sort.Order.desc("adId")))
                .stream()
                .limit(5)  // Only take the top 5 latest records
                .collect(Collectors.toList());
        return ResponseEntity.ok(announcements);
    }

    @PostMapping("/create")
    public ResponseEntity<?> createAnnouncement(@RequestBody Map<String, Object> payload) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            System.out.println("Authentication: " + authentication);
            // Retrieve current user
            String username = SecurityContextHolder.getContext().getAuthentication().getName();
            System.out.println("Retrieved username: " + username);

            User currentUser = userService.findUserByUsername(username);
            System.out.println("Retrieved user: " + currentUser);

            if (currentUser == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(Map.of("error", "User not found or not logged in"));
            }

            String title = (String) payload.get("title");
            String description = (String) payload.get("description");
            String email = (String) payload.get("contact_email");
            String telephone = (String) payload.get("contact_phone");
            List<String> images = (List<String>) payload.get("images");
            Long categoryId = Long.valueOf((String) payload.get("category")); // Retrieve the category ID (correcting type to String -> Long)

            // Get the Category object by ID
            Optional<Category> categoryOptional = categoryRepository.findById(categoryId);
            if (!categoryOptional.isPresent()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(Map.of("error", "Invalid category ID"));
            }
            Category category = categoryOptional.get();

            // Create Announcement
            Announcement announcement = new Announcement();
            announcement.setTitle(title);
            announcement.setDescription(description);
            announcement.setContactEmail(email);
            announcement.setContactPhone(telephone);
            announcement.setCategory(categoryId);  // Set the Category object
            announcement.setUserId(currentUser.getId()); // Set user ID
            announcement.setCreatedAt(LocalDateTime.now()); // Set current timestamp

            // Save Announcement first to get its ID
            Announcement savedAnnouncement = announcementRepository.save(announcement);

            // Save Images
            for (String base64Image : images) {
                AnnouncementImage image = new AnnouncementImage();
                image.setImage(base64Image);
                image.setAnnouncement(savedAnnouncement);
                announcementImageRepository.save(image);
            }

            return ResponseEntity.ok(Map.of("message", "Announcement created successfully!"));
        } catch (Exception e) {
            // Returning error in JSON format
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Error: " + e.getMessage()));
        }
    }

    // fetching categories for dropdown
    @GetMapping("/categories")
    public ResponseEntity<List<Map<String, Object>>> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        List<Map<String, Object>> response = categories.stream().map(category -> {
            Map<String, Object> map = new HashMap<>();
            map.put("id", category.getCategoryId());
            map.put("name", category.getCategoryName());
            return map;
        }).toList();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<Map<String, Object>> listAnnouncementsByUser(
            @PathVariable Long userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    )
    {
        Pageable pageable = PageRequest.of(page, size);
        Page<Announcement> announcementPage = announcementRepository.findByUserId(userId, pageable);

        List<Map<String, Object>> announcementsWithImages = announcementPage.getContent().stream().map(announcement -> {
            Map<String, Object> annMap = new HashMap<>();
            annMap.put("id", announcement.getAdId());
            annMap.put("title", announcement.getTitle());
            annMap.put("description", announcement.getDescription());
            annMap.put("contactEmail", announcement.getContactEmail());
            annMap.put("contactPhone", announcement.getContactPhone());
            annMap.put("nickName", announcement.getUserId());

            List<String> base64Images = announcement.getImages().stream()
                    .map(AnnouncementImage::getImage)
                    .toList();

            annMap.put("images", base64Images);
            return annMap;
        }).toList();

        Map<String, Object> response = new HashMap<>();
        response.put("content", announcementsWithImages);
        response.put("totalElements", announcementPage.getTotalElements());
        response.put("totalPages", announcementPage.getTotalPages());
        response.put("currentPage", announcementPage.getNumber());

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/delete/{adId}")
    public ResponseEntity<?> deleteAnnouncement(@PathVariable Long adId) {
        Optional<Announcement> announcementOptional = announcementRepository.findById(adId);
        if (!announcementOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Announcement not found");
        }

        announcementRepository.deleteById(adId);  // Delete the announcement
        return ResponseEntity.ok(Map.of("message", "Announcement deleted successfully!"));
    }

    @PutMapping("/update/{adId}")
    public ResponseEntity<?> updateAnnouncement(
            @PathVariable Long adId,
            @RequestBody Map<String, Object> payload) {
        try {
            // Fetch the existing announcement
            Optional<Announcement> announcementOptional = announcementRepository.findById(adId);
            if (!announcementOptional.isPresent()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Announcement not found");
            }

            Announcement announcement = announcementOptional.get();

            // Update fields if present in the request payload
            if (payload.containsKey("title")) {
                announcement.setTitle((String) payload.get("title"));
            }
            if (payload.containsKey("description")) {
                announcement.setDescription((String) payload.get("description"));
            }
            if (payload.containsKey("contact_email")) {
                announcement.setContactEmail((String) payload.get("contact_email"));
            }
            if (payload.containsKey("contact_phone")) {
                announcement.setContactPhone((String) payload.get("contact_phone"));
            }
            if (payload.containsKey("category")) {
                Long categoryId = Long.valueOf((String) payload.get("category"));
                Optional<Category> categoryOptional = categoryRepository.findById(categoryId);
                if (!categoryOptional.isPresent()) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                            .body(Map.of("error", "Invalid category ID"));
                }
                announcement.setCategory(categoryId);
            }

            // Handle images
            if (payload.containsKey("images")) {
                // Clear old images
                List<AnnouncementImage> oldImages = announcement.getImages();
                oldImages.clear(); // Use orphan removal to delete from the DB

                // Save new images
                List<String> newImages = (List<String>) payload.get("images");
                for (String base64Image : newImages) {
                    AnnouncementImage announcementImage = new AnnouncementImage();
                    announcementImage.setAnnouncement(announcement);
                    announcementImage.setImage(base64Image);
                    oldImages.add(announcementImage); // Attach to announcement
                }
            } else {
                // If images are not provided in the payload, retain existing images.
                System.out.println("No new images provided, retaining existing ones.");
            }

            // Update the updatedAt timestamp
            announcement.setUpdatedAt(LocalDateTime.now());

            // Save the updated announcement
            Announcement updatedAnnouncement = announcementRepository.save(announcement);

            return ResponseEntity.ok(Map.of("message", "Announcement updated successfully!",
                    "updatedAnnouncement", updatedAnnouncement));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Error: " + e.getMessage()));
        }
    }
}

