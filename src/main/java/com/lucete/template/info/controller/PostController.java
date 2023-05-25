package com.lucete.template.info.controller;

import com.lucete.template.info.model.Post;
import com.lucete.template.info.service.PostService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/post")
public class PostController {
    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("/{boardId}")
    public List<Post> getPostsByBoardId(@PathVariable Long boardId) {
        return postService.getPostsByBoardId(boardId);
    }

    @GetMapping("/post/{id}")
    public Post getPost(@PathVariable Long id) {
        return postService.getPostById(id);
    }

    @PostMapping
    public ResponseEntity<Post> addPost(@RequestBody Post newPost) {
        Post savedPost = postService.addPost(newPost);
        return ResponseEntity.ok(savedPost);
    }

    @PutMapping("/{id}")
    public Post updatePost(@PathVariable Long id, @RequestBody Post updatedPost) {
        updatedPost.setId(id);
        return postService.updatePost(updatedPost);
    }

    @DeleteMapping("/{id}")
    public void deletePost(@PathVariable Long id) {
        postService.deletePost(id);
    }
}
