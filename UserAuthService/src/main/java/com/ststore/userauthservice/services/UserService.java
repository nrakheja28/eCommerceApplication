package com.ststore.userauthservice.services;

import com.ststore.userauthservice.models.User;
import com.ststore.userauthservice.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    public User getUser(Long id) {

        return userRepository.findById(id).get();
    }
}
