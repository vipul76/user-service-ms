package com.ms.userservicems.services;

import com.ms.userservicems.dtos.SetUserRolesRequestDto;
import com.ms.userservicems.dtos.UserDto;
import com.ms.userservicems.models.Role;
import com.ms.userservicems.models.User;
import com.ms.userservicems.repositories.RoleRepository;
import com.ms.userservicems.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public UserService(UserRepository userRepository,RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    public UserDto getUserDetails(Long userId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        return optionalUser.map(UserDto::from).orElse(null);
    }

    public UserDto setUserRoles(Long userId, SetUserRolesRequestDto request) {
        Optional<User> userOption = userRepository.findById(userId);
        List<Role> roles = roleRepository.findAllByIdIn(request.getRoleIds());

        if(userOption.isEmpty()){
            return null;
        }

        User user =  userOption.get();
        user.setRoles(Set.copyOf(roles));

        User savedUser = userRepository.save(user);
        return UserDto.from(savedUser);
    }
}