package com.ststore.userauthservice.repositories;

import com.ststore.userauthservice.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);
    User save(User user);
    Optional<User> findById(Long id);
}
