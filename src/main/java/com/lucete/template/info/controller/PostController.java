package com.lucete.template.info.controller;

import com.lucete.template.info.DTO.CommentDTO;
import com.lucete.template.info.DTO.PostDTO;
import com.lucete.template.info.model.Post;
import com.lucete.template.info.service.CommentService;
import com.lucete.template.info.service.PostService;
import com.lucete.template.info.service.ScheduleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/posts")
@Tag(name = "posts", description = "게시물 API")

public class PostController {

    private final PostService postService;
    public PostController(PostService postService) {
        this.postService = postService;
    }


    @GetMapping("/{postId}")
    @Operation(summary = "특정 게시물 정보 조회", description = "게시물 ID를 이용하여 게시물 정보를 조회합니다.")
    public ResponseEntity<PostDTO> getPostById(@PathVariable Long postId) {
        return ResponseEntity.ok(postService.getPostById(postId));
    }

    @PostMapping
    @Operation(summary = "새로운 게시물 생성", description = "새로운 게시물 정보를 입력하여 생성합니다.")

    public ResponseEntity<PostDTO> createPost(@RequestBody PostDTO postDto) {
        return ResponseEntity.ok(postService.createPost(postDto));
    }

    @PutMapping("/{postId}")
    @Operation(summary = "게시물 정보 수정", description = "게시물 ID를 이용하여 기존 게시물 정보를 수정합니다.")

    public ResponseEntity<PostDTO> updatePost(@PathVariable Long postId, @RequestBody PostDTO postDto) {
        return ResponseEntity.ok(postService.updatePost(postId, postDto));
    }

    @DeleteMapping("/{postId}")
    @Operation(summary = "게시물 삭제", description = "게시물 ID를 이용하여 게시물 정보를 삭제합니다.")

    public ResponseEntity<?> deletePost(@PathVariable Long postId) {
        postService.deletePost(postId);
        return ResponseEntity.ok().build();
    }
    @GetMapping
    @Operation(summary = "모든 게시물 정보 조회", description = "페이징 기능을 이용하여 모든 게시물 정보를 조회합니다.")

    public Page<PostDTO> getAllPosts(@RequestParam(required = false, defaultValue = "0") int page,
                                     @RequestParam(required = false, defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return postService.getAllPosts(pageable);
    }
    @GetMapping("/boardID/{boardId}")
    @Operation(summary = "특정 게시판에 속한 모든 게시물 조회", description = "게시판 ID를 이용하여 해당 게시판에 속한 모든 게시물을 조회합니다.")
    public ResponseEntity<List<PostDTO>> getPostsByBoardId(@Parameter(description = "게시물을 조회할 게시판의 ID") @PathVariable Long boardId) {
        List<PostDTO> posts = postService.getPostsByBoardId(boardId);
        return ResponseEntity.ok(posts);
    }


}
