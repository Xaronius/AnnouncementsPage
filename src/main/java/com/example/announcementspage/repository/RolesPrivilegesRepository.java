package com.example.announcementspage.repository;

import commons.entities.Role;
import commons.entities.RolesPrivileges;
import commons.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RolesPrivilegesRepository extends JpaRepository<RolesPrivileges, Long> {
    List<RolesPrivileges> findByUserAndRole(User user, Role role);
}
