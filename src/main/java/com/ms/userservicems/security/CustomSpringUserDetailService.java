package com.ms.userservicems.security;

import com.ms.userservicems.models.User;
import com.ms.userservicems.repositories.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomSpringUserDetailService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomSpringUserDetailService(UserRepository userRepository){
        this.userRepository=userRepository;
    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<User> optionalUser = userRepository.findByEmail(username);
        if(optionalUser.isEmpty()){
            throw new UsernameNotFoundException("User with given username doesn't exist");
        }

        User user = optionalUser.get();
        return new CustomUserDetails(user);
    }
}
