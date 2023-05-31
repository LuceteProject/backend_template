package com.lucete.template.info.repository;

import com.lucete.template.info.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findByBoardId(Long boardId);
    @Query("SELECT p FROM Post p LEFT JOIN FETCH p.user u LEFT JOIN FETCH p.board b WHERE p.id = :id")
    Optional<Post> findWithUserAndBoardById(@Param("id") Long id);
}
