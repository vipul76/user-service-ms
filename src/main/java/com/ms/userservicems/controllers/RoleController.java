package com.ms.userservicems.controllers;

import com.ms.userservicems.dtos.CreateRoleRequestDto;
import com.ms.userservicems.models.Role;
import com.ms.userservicems.services.RoleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value="/roles")
public class RoleController {

    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @PostMapping
    public ResponseEntity<Role> createRole(@RequestBody CreateRoleRequestDto request){
        Role role = roleService.save(request.getName());
        return new ResponseEntity<>(role, HttpStatus.OK);
    }
}
