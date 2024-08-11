package com.ststore.userauthservice.dtos;

import com.ststore.userauthservice.models.Role;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SignupRequestDto {
    private String username;
    private String password;
    private List<Role> roles;
}
