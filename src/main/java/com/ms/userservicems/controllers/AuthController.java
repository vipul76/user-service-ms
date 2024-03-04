package com.ms.userservicems.controllers;

import com.ms.userservicems.dtos.*;
import com.ms.userservicems.services.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<UserDto> login(@RequestBody LoginRequestDto requestDto){
        return authService.login(requestDto.getEmail(),requestDto.getPassword());
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestBody LogoutRequestDto logoutRequestDto){
        return new ResponseEntity<>(logoutRequestDto,HttpStatus.OK);
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signUp(@RequestBody signUpRequestDto signUpRequestDto){
        authService.signUo(signUpRequestDto.getEmail(),signUpRequestDto.getPassword());
        return new ResponseEntity<>(signUpRequestDto,HttpStatus.OK);
    }

    @PostMapping("/validate")
    public ResponseEntity<?> validateToken(@RequestBody ValidateTokenRequestDto validateTokenRequestDto){
        return new ResponseEntity<>(validateTokenRequestDto,HttpStatus.OK);
    }
}