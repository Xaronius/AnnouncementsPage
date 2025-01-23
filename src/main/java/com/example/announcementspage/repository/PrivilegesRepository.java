package com.example.announcementspage.repository;

import commons.entities.Privileges;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PrivilegesRepository extends JpaRepository<Privileges, Long> {
    Privileges findByName(String name);
}
