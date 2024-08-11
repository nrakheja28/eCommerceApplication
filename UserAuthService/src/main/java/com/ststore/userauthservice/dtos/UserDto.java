package com.ststore.userauthservice.dtos;

import com.ststore.userauthservice.models.Role;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UserDto {
    private String username;
    private List<Role> roles;
}
