package com.ststore.userauthservice.controllers;

import com.ststore.userauthservice.dtos.UserDto;
import com.ststore.userauthservice.models.User;
import com.ststore.userauthservice.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/{id}")
    public UserDto getUserDetail(@PathVariable Long id){
        User user = userService.getUser(id);
        return userToDto(user);
    }

    private UserDto userToDto(User user){
        UserDto userDto = new UserDto();
        userDto.setUsername(user.getUsername());
//        userDto.setRoles(user.getRoles());
        return userDto;
    }
}
