package com.ms.userservicems.services;

import com.ms.userservicems.dtos.UserDto;
import com.ms.userservicems.models.Session;
import com.ms.userservicems.models.SessionStatus;
import com.ms.userservicems.models.User;
import com.ms.userservicems.repositories.SessionRepository;
import com.ms.userservicems.repositories.UserRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.MacAlgorithm;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMapAdapter;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final SessionRepository sessionRepository;
    private final BCryptPasswordEncoder bcryptPasswordEncoder;

    public AuthService(UserRepository userRepository,
                       SessionRepository sessionRepository,
                       BCryptPasswordEncoder bcryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.sessionRepository = sessionRepository;
        this.bcryptPasswordEncoder = bcryptPasswordEncoder;
    }


    public ResponseEntity<UserDto> login(String email, String password) {
        Optional<User> userOptional = userRepository.findByEmail(email);

        if (userOptional.isEmpty()){
            return null;
        }

        User user = userOptional.get();

        if(!bcryptPasswordEncoder.matches(password, user.getPassword())){
            throw new RuntimeException("Password mismatch");
        }

        MacAlgorithm alg = Jwts.SIG.HS256;
        SecretKey key = alg.key().build();

        Map<String, Object> jsonMap = new HashMap<>();
        jsonMap.put("email",user.getEmail());
        jsonMap.put("role",user.getRoles());
        jsonMap.put("createdAt",new Date());
        jsonMap.put("expiryAt",DateUtils.addDays(new Date(),7));

        String jws = Jwts.builder()
                .claims(jsonMap)
                .signWith(key,alg)
                .compact();

        Session session =  new Session();
        session.setSessionStatus(SessionStatus.ACTIVE);
        session.setToken(jws);
        session.setUser(user);
        sessionRepository.save(session);

        UserDto userDto = new UserDto();
        userDto.setEmail(user.getEmail());

        MultiValueMapAdapter<String, String> headers = new MultiValueMapAdapter<>(new HashMap<>());
        headers.add(HttpHeaders.SET_COOKIE, "auth-token:"+jws);

        return new ResponseEntity<>(userDto,headers,HttpStatus.OK);
    }

    public void signUp(String email, String password) {
    }
}
