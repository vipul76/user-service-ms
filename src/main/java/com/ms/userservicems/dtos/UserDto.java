package com.ms.userservicems.dtos;

import com.ms.userservicems.models.Role;
import com.ms.userservicems.models.User;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class UserDto {

    private String email;
    private Set<Role> roles = new HashSet<>();

    public static UserDto from(User user) {
        UserDto userDto = new UserDto();
        userDto.email = user.getEmail();
        userDto.roles = user.getRoles();
        return userDto;
    }
}
