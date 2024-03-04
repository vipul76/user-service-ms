package com.ms.userservicems.services;

import com.ms.userservicems.models.Role;
import com.ms.userservicems.repositories.RoleRepository;
import org.springframework.stereotype.Service;

@Service
public class RoleService {

    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public Role save(String name) {
        Role role = new Role();
        role.setRole(name);
        return roleRepository.save(role);
    }
}
