package com.ms.userservicems.services;

import com.ms.userservicems.dtos.UserDto;
import com.ms.userservicems.models.Session;
import com.ms.userservicems.models.SessionStatus;
import com.ms.userservicems.models.User;
import com.ms.userservicems.repositories.SessionRepository;
import com.ms.userservicems.repositories.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final SessionRepository sessionRepository;
    //private final BCryptPasswordEncoder bcryptPasswordEncoder;

    public AuthService(UserRepository userRepository,
                       SessionRepository sessionRepository) {
        this.userRepository = userRepository;
        this.sessionRepository = sessionRepository;
    }


    public ResponseEntity<UserDto> login(String email, String password) {
        Optional<User> userOptional = userRepository.findByEmail(email);

        if (userOptional.isEmpty()){
            return null;
        }

        User user = userOptional.get();
        if(!user.getPassword().matches(password)){
            return null;
        }

        Session session =  new Session();
        session.setUser(user);
        session.setSessionStatus(SessionStatus.ACTIVE);
        sessionRepository.save(session);

        UserDto userDto = new UserDto();
        userDto.setEmail(user.getEmail());

        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }

    public void signUo(String email, String password) {
    }
}
