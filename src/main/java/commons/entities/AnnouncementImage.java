package commons.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.Base64;

@Entity
@Table(name = "announcement_images")
public class AnnouncementImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "image_id")  // Maps to image_id column in DB
    private Long imageId;

    @ManyToOne
    @JoinColumn(name = "announcement_id", nullable = false)  // Maps to announcement_id in DB
    @JsonIgnore
    private Announcement announcement;

    @Lob
    @Column(name = "image")
    private String image;

    public AnnouncementImage() {}

    public AnnouncementImage(Announcement announcement, byte[] image) {
        this.announcement = announcement;
        this.image = encodeImage(image);
    }

    // Method to encode byte[] to Base64 String
    public String encodeImage(byte[] imageBytes) {
        return Base64.getEncoder().encodeToString(imageBytes);
    }

    // Getters and Setters
    public Long getImageId() {
        return imageId;
    }

    public void setImageId(Long imageId) {
        this.imageId = imageId;
    }

    public Announcement getAnnouncement() {
        return announcement;
    }

    public void setAnnouncement(Announcement announcement) {
        this.announcement = announcement;
    }
    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
