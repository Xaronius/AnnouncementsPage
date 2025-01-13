package com.example.announcementspage.repository;

import commons.entities.AnnouncementImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AnnouncementImageRepository extends JpaRepository<AnnouncementImage, Long> {
    List<AnnouncementImage> findByAnnouncement_AdId(Long adId);
}