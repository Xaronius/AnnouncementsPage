package com.example.announcementspage;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan("commons.entities")
public class AnnouncementsPageApplication {

    public static void main(String[] args) {
        SpringApplication.run(AnnouncementsPageApplication.class, args);
    }

}
