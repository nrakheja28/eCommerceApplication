package com.ststore.userauthservice.controllers;

import com.ststore.userauthservice.dtos.*;
import com.ststore.userauthservice.models.User;
import com.ststore.userauthservice.services.AuthService;
import org.antlr.v4.runtime.misc.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<UserDto>  signup(@RequestBody SignupRequestDto signupRequestDto){

        System.out.println("Request reached controller");
        User user = authService.signup(signupRequestDto.getUsername(), signupRequestDto.getPassword(), signupRequestDto.getRoles());
        return new ResponseEntity<>(userToDto(user), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<UserDto> login(@RequestBody LoginRequestDto loginRequestDto){
        Pair<User, String> response = authService.login(loginRequestDto.getUsername(), loginRequestDto.getPassword());

        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add(HttpHeaders.SET_COOKIE, response.b);

        return new ResponseEntity<>(userToDto(response.a), headers, HttpStatus.OK);
    }

    @PostMapping("/logout")
    public void logout(@RequestBody LogoutRequestDto logoutRequestDto){

    }

    @PostMapping("/validateToken")
    public ResponseEntity<String> validateToken(@RequestBody ValidateTokenRequestDto validateTokenRequestDto){
        Boolean result = authService.validateToken(validateTokenRequestDto.getToken(), validateTokenRequestDto.getUsername());
        if(result){
            return new ResponseEntity<>("Authorized", HttpStatus.OK);
        }
        return new ResponseEntity<>("Unauthorized", HttpStatus.UNAUTHORIZED);
    }

    @PostMapping("/forgotPassword")
    public void forgotPassword(@RequestBody ForgotPasswordRequestDto forgotPasswordRequestDto){

    }

    private UserDto userToDto(User user){
        UserDto userDto = new UserDto();
        userDto.setUsername(user.getUsername());
//        userDto.setRoles(user.getRoles());
        return userDto;
    }


}
