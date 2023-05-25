package com.lucete.template.info.controller;

import com.lucete.template.info.model.Comment;
import com.lucete.template.info.service.CommentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comment")
public class CommentController {
    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping("/{postId}")
    public List<Comment> getCommentsByPostId(@PathVariable Long postId) {
        return commentService.getCommentsByPostId(postId);
    }

    @GetMapping("/comment/{id}")
    public Comment getComment(@PathVariable Long id) {
        return commentService.getCommentById(id);
    }

    @PostMapping
    public ResponseEntity<Comment> addComment(@RequestBody Comment newComment) {
        Comment savedComment = commentService.addComment(newComment);
        return ResponseEntity.ok(savedComment);
    }

    @PutMapping("/{id}")
    public Comment updateComment(@PathVariable Long id, @RequestBody Comment updatedComment) {
        updatedComment.setId(id);
        return commentService.updateComment(updatedComment);
    }

    @DeleteMapping("/{id}")
    public void deleteComment(@PathVariable Long id) {
        commentService.deleteComment(id);
    }
}
