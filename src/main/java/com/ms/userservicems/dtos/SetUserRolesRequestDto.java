package com.ms.userservicems.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class SetUserRolesRequestDto {
    private List<Long> roleIds;
}