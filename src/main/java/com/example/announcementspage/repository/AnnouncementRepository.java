package com.example.announcementspage.repository;
import commons.entities.Announcement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnnouncementRepository extends JpaRepository<Announcement, Long> {
    // JpaRepository already provides basic CRUD methods
}
