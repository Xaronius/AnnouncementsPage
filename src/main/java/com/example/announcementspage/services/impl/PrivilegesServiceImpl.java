package com.example.announcementspage.services.impl;

import com.example.announcementspage.repository.PrivilegesRepository;
import com.example.announcementspage.repository.RolesPrivilegesRepository;
import com.example.announcementspage.repository.UserRepository;
import com.example.announcementspage.services.PrivilegesService;
import commons.entities.Privileges;
import commons.entities.Role;
import commons.entities.RolesPrivileges;
import commons.entities.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class PrivilegesServiceImpl implements PrivilegesService {
    private final RolesPrivilegesRepository rolesPrivilegesRepository;
    private final UserRepository userRepository;
    private final PrivilegesRepository privilegesRepository;

    public PrivilegesServiceImpl(RolesPrivilegesRepository rolesPrivilegesRepository, UserRepository userRepository, PrivilegesRepository privilegesRepository) {
        this.rolesPrivilegesRepository = rolesPrivilegesRepository;
        this.userRepository = userRepository;
        this.privilegesRepository = privilegesRepository;
    }

    @Override
    public Boolean checkPrivilege(User user, String privilege) {
        if (Objects.isNull(user))
            return false;
        else if (Boolean.TRUE.equals(user.getIsAdmin()))
            return true;

        Role role = user.getRole();
        if (Objects.isNull(role))
            return false;
        Privileges needed = privilegesRepository.findByName(privilege);
        List<RolesPrivileges> userPrivileges = rolesPrivilegesRepository.findByUserAndRole(user, role);
        Optional<RolesPrivileges> finded = userPrivileges.stream().filter(f ->
                                f.getPrivilege() != null &&
                                f.getPrivilege().getId().equals(needed.getId()) &&
                                Boolean.TRUE.equals(needed.getIsActive())).findFirst();

        return finded.isPresent();
    }
}
