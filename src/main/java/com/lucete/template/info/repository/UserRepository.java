package com.lucete.template.info.repository;

import com.lucete.template.info.model.User;
import com.lucete.template.info.repository.mapping.UserInfoMapping;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByName(String username);
    Optional<User> findByEmail(String email);
    //@Query("SELECT u.name, u.email from User u")
    List<UserInfoMapping> findAllBy();

}
