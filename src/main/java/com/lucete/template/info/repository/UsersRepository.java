package com.lucete.template.info.repository;

import com.lucete.template.info.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsersRepository extends JpaRepository<User, Long> {
    User findByName(String username);
    Optional<User> findByEmail(String email);
// JpaRepository 인터페이스에는 이미 다음과 같은 메서드들이 정의되어 있습니다:
    // - List<T> findAll();
    // - Optional<T> findById(ID id);
    // - <S extends T> S save(S entity);
    // - void deleteById(ID id);
}
