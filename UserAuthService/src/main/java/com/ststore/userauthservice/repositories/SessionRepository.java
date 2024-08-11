package com.ststore.userauthservice.repositories;

import com.ststore.userauthservice.models.Session;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SessionRepository extends JpaRepository<Session, Long> {

    Session save(Session session);

    Optional<Session> findByTokenEquals(String token);

}
