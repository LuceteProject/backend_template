package com.lucete.template.info.repository;

import com.lucete.template.info.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findByBoardId(Long boardId);
}
