package com.ms.userservicems.controllers;

import com.ms.userservicems.dtos.SetUserRolesRequestDto;
import com.ms.userservicems.dtos.UserDto;
import com.ms.userservicems.models.Role;
import com.ms.userservicems.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value="/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{id}")
    private ResponseEntity<UserDto> getUserDetails(@PathVariable("id") Long userId){
        UserDto userDto = userService.getUserDetails(userId);
        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }

    @PostMapping("/{id}/roles")
    private ResponseEntity<UserDto> setUserRoles(@PathVariable("id") Long userId, @RequestBody SetUserRolesRequestDto request){
        UserDto userDto = userService.setUserRoles(userId,request);
        return new ResponseEntity<>(userDto,HttpStatus.OK);
    }

}
