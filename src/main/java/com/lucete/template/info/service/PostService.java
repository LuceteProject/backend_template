package com.lucete.template.info.service;

import com.lucete.template.info.model.Post;
import com.lucete.template.info.repository.PostRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class PostService {
    private final PostRepository postRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public Post addPost(Post post) {
        return postRepository.save(post);
    }

    public List<Post> getPostsByBoardId(Long boardId) {
        return postRepository.findByBoardId(boardId);
    }

    public Post getPostById(Long id) {
        return postRepository.findById(id).orElse(null);
    }

    public Post updatePost(Post post) {
        return postRepository.save(post);
    }

    public void deletePost(Long id) {
        postRepository.deleteById(id);
    }
}