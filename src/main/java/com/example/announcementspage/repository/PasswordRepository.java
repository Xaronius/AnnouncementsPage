package com.example.announcementspage.repository;

import commons.entities.Password;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PasswordRepository extends JpaRepository<Password, Long> {

}
