package com.ststore.userauthservice.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ststore.userauthservice.clients.KafkaProducerClient;
import com.ststore.userauthservice.dtos.MailDto;
import com.ststore.userauthservice.exceptions.IncorrectPasswordException;
import com.ststore.userauthservice.exceptions.UserAlreadyExistException;
import com.ststore.userauthservice.exceptions.UserNotFoundException;
import com.ststore.userauthservice.models.Role;
import com.ststore.userauthservice.models.Session;
import com.ststore.userauthservice.models.SessionStatus;
import com.ststore.userauthservice.models.User;
import com.ststore.userauthservice.repositories.SessionRepository;
import com.ststore.userauthservice.repositories.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import org.antlr.v4.runtime.misc.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.*;

@Service
public class AuthService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    SecretKey secretKey;

    @Autowired
    SessionRepository sessionRepository;

    @Autowired
    KafkaProducerClient kafkaProducerClient;

    @Autowired
    private ObjectMapper objectMapper;

    public User signup(String username, String password, List<Role> roles) {

        Optional<User> user = userRepository.findByUsername(username);
        if(user.isPresent()) throw new UserAlreadyExistException("User Already Exist with username: " + username);
        User newUser = new User();
        newUser.setUsername(username);
        newUser.setPassword(bCryptPasswordEncoder.encode(password));
        newUser.setRoles(roles);

        MailDto mailDto = new MailDto();
        mailDto.setFrom("ststore.internal@gmail.com");
        mailDto.setTo("nrakheja28@gmail.com");
        mailDto.setSubject("Welcome to Estore");
        mailDto.setBody("Hi " + username +  ", <br> We welcome you to our Estore Application, Thanks for being part of our family. Hope you like our products and order soon.");
        try {
            kafkaProducerClient.sendMessage("signup", objectMapper.writeValueAsString(mailDto));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return userRepository.save(newUser);
    }

    public Pair<User, String> login(String username, String password) {
        Optional<User> user = userRepository.findByUsername(username);
        if(user.isEmpty()) throw new UserNotFoundException("User Not Found with username: " + username);
        if(!bCryptPasswordEncoder.matches(password, user.get().getPassword())) throw new IncorrectPasswordException();
        Map<String,Object> jwtData = new HashMap<>();
        jwtData.put("username", user.get().getUsername());
        jwtData.put("iat", System.currentTimeMillis());
        jwtData.put("exp", System.currentTimeMillis() + 600000);
        String token = Jwts.builder().claims(jwtData).signWith(secretKey).compact();
        Session session = new Session();
        session.setToken(token);
        session.setUser(user.get());
        session.setStatus(SessionStatus.ACTIVE);
        sessionRepository.save(session);
        return new Pair<>(user.get(), token);
    }

    public Boolean validateToken(String token, String username) {

        Optional<Session> session = sessionRepository.findByTokenEquals(token);
        if(session.isEmpty() || session.get().getStatus().equals(SessionStatus.EXPIRED)) {
            return false;
        }
        JwtParser parser = Jwts.parser().verifyWith(secretKey).build();
        Claims claims = parser.parseSignedClaims(token).getPayload();
        if( (Long)claims.get("exp") <= System.currentTimeMillis() ) {
            return false;
        }
        Optional<User> user = userRepository.findByUsername(username);
        if(user.isEmpty()) return false;
        return user.get().getUsername().equals((String) claims.get("username"));

    }

}
