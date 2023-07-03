package com.lucete.template.info.controller;

import com.lucete.template.info.DTO.CommentDTO;
import com.lucete.template.info.model.Comment;
import com.lucete.template.info.service.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/comments")
@Tag(name = "comments", description = "댓글 API")
public class CommentController {
    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping
    @Operation(summary = "새로운 댓글 생성", description = "새로운 댓글 정보를 입력하여 생성합니다.")

    public CommentDTO createComment(@RequestBody CommentDTO commentDTO) {
        return commentService.createComment(commentDTO);
    }

    @GetMapping("/{commentId}")
    @Operation(summary = "특정 댓글 정보 조회", description = "댓글 ID를 이용하여 댓글 정보를 조회합니다.")
    public CommentDTO getComment(@PathVariable Long commentId) {
        return commentService.getComment(commentId);
    }
    @GetMapping("/{postId}")
    @Operation(summary = "특정 게시물에 속한 모든 댓글 조회", description = "게시물 ID를 이용하여 해당 게시물에 속한 모든 댓글을 조회합니다.")
    public ResponseEntity<List<CommentDTO>> getCommentsByPostId(@Parameter(description = "댓글을 조회할 게시물의 ID") @PathVariable Long postId) {
        List<CommentDTO> comments = commentService.getCommentsByPostId(postId);
        return ResponseEntity.ok(comments);
    }
    @PutMapping("/{commentId}")
    @Operation(summary = "댓글 정보 수정", description = "댓글 ID를 이용하여 기존 댓글 정보를 수정합니다.")

    public CommentDTO updateComment(@PathVariable Long commentId, @RequestBody CommentDTO commentDTO) {
        return commentService.updateComment(commentId, commentDTO);
    }

    @DeleteMapping("/{commentId}")
    @Operation(summary = "댓글 삭제", description = "댓글 ID를 이용하여 댓글 정보를 삭제합니다.")
    public void deleteComment(@PathVariable Long commentId) {
        commentService.deleteComment(commentId);
    }
    @GetMapping
    @Operation(summary = "모든 댓글 조회")
    public ResponseEntity<List<CommentDTO>> getAllComments() {
        return ResponseEntity.ok(commentService.getAllComments());
    }
}
