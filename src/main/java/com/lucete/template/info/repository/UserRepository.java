package com.lucete.template.info.repository;

import com.lucete.template.info.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByName(String username);
    Optional<User> findByEmail(String email);
    Page<User> findAll(Pageable pageable);
}
